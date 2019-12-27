package com.justdo.common.filter;

import com.alibaba.fastjson.JSON;
import com.justdo.common.redis.shiro.RedisCacheManager;
import com.justdo.common.utils.R;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 踢出已经登录的用户过滤器
 *
 * <p> 1.读取当前登录用户名，获取在缓存中的sessionId队列</p>
 * <p> 2.判断队列的长度，大于最大登录限制的时候，按踢出规则将之前的sessionId中的session域中存入kickout：true，并更新队列缓存</p>
 * <p> 3.判断当前登录的session域中的kickout如果为true，想将其做退出登录处理，然后再重定向到踢出登录提示页面</p>
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:36:21
 */
public class KickoutSessionControlFilter extends AccessControlFilter {


	private static final Logger logger = LoggerFactory.getLogger(KickoutSessionControlFilter.class);
	//踢出后到的地址
	private String kickoutUrl;
	//踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
	private boolean kickoutAfter = false;
	//同一个帐号最大会话数 默认1
	private int maxSession = 1;
	//The Redis key prefix for caches
	private String keyPrefix = "shiro:cache:loginlimit:";

	private DefaultWebSessionManager sessionManager;

	private Cache<String, Deque<Serializable>> cache;

	private RedisCacheManager redisCacheManager;

	public void setKickoutUrl(String kickoutUrl) {
		this.kickoutUrl = kickoutUrl;
	}

	public void setKickoutAfter(boolean kickoutAfter) {
		this.kickoutAfter = kickoutAfter;
	}

	public void setMaxSession(int maxSession) {
		this.maxSession = maxSession;
	}

	public void setSessionManager(DefaultWebSessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	/**
	 * 设置Cache的key的前缀
	 */
	public void setCacheManager(RedisCacheManager cacheManager) {
		redisCacheManager = cacheManager;
		redisCacheManager.setKeyPrefix(keyPrefix);
	}

	/**
	 * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
	 * (感觉这里应该是对白名单（不需要登录的接口）放行的)
	 * 如果isAccessAllowed返回true则onAccessDenied方法不会继续执行
	 *
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 * @throws Exception
	 */

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String url = httpRequest.getRequestURI();

		if(url.contains("justdo/login")||url.contains("logout")||url.contains("verification")){
			return true;
		}

		Subject subject = getSubject(request, response);

		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			//如果没有登录，直接进行登录的流程
			return true;
		}

		//DefaultFilter
		Session session = subject.getSession();

		SimpleEmployeeDO simpleEmployeeDO = (SimpleEmployeeDO) subject.getPrincipal();

		String username = simpleEmployeeDO.getLoginName();

		//如果被踢出了，直接退出，重定向到踢出后的地址

		if ((Boolean)session.getAttribute("kickout")!=null&&(Boolean)session.getAttribute("kickout") == true){
			//会话被踢出了
			try {
				//退出登录
				logger.info("被踢出了：sesionId："+ session.getId()+"，username:"+username);
				subject.logout();
			} catch (Exception e) { //ignore
				logger.error("踢出logout异常：sesionId："+ session.getId()+"，username:"+username);
			}
			saveRequest(request);
//			Map<String, String> resultMap = new HashMap<String, String>();
			String a = ((HttpServletRequest) request).getHeader("X-Requested-With");
			//判断是不是Ajax请求
			if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
//				resultMap.put("user_status", "300");
//				resultMap.put("message", "您已经在其他地方登录，请重新登录！");
				//输出json串
				out(response,  R.error(-11,"您已经在其他地方登录，请重新登录！"));
			}else{
				//重定向
				WebUtils.issueRedirect(request, response, kickoutUrl);
			}
			return false;
		}

		Serializable sessionId = session.getId();
		redisCacheManager.setPrincipalIdFieldName(simpleEmployeeDO.getId());
		this.cache = redisCacheManager.getCache(username);
		//读取缓存   没有就存入
		Deque<Serializable> deque = cache.get(username);

		//如果此用户没有session队列，也就是还没有登录过，缓存中没有
		//就new一个空队列，不然deque对象为空，会报空指针
		if(deque==null){
			deque = new LinkedList<Serializable>();
		}
		//如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if(!deque.contains(sessionId) && session.getAttribute("kickout") == null) {
			//将sessionId存入队列
			deque.push(sessionId);
			//将用户的sessionId队列缓存
			cache.put(username, deque);
		}

		//如果队列里的sessionId数超出最大会话数，开始踢人
		while(deque.size() > maxSession) {
			Serializable kickoutSessionId = null;
			if(kickoutAfter) { //如果踢出后者
				kickoutSessionId = deque.removeFirst();
				//踢出后再更新下缓存队列
				cache.put(username, deque);
			} else { //否则踢出前者
				kickoutSessionId = deque.removeLast();
				//踢出后再更新下缓存队列
				cache.put(username, deque);
			}

			try {
				//获取被踢出的sessionId的session对象
				Session kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if(kickoutSession != null) {
					//设置会话的kickout属性表示踢出了
					logger.info("踢出设置属性：sesionId："+ kickoutSession.getId());
					kickoutSession.setAttribute("kickout", true);
				}
			} catch (Exception e) {//ignore exception

				logger.error("踢出设置异常:"+e.getMessage());
			}
		}
		return true;

	}


	/**
	 * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；
	 * 如果返回false表示该拦截器实例已经处理了，将直接返回即可。
	 * onAccessDenied是否执行取决于isAccessAllowed的值，
	 * 如果返回true则onAccessDenied不会执行；如果返回false，执行onAccessDenied
	 * 如果onAccessDenied也返回false，则直接返回，
	 * 不会进入请求的方法（只有isAccessAllowed和onAccessDenied的情况下）
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		//退出
		Subject subject = getSubject(request, response);

		subject.logout();
		//保存访问路径
		saveRequest(request);
		//重定向
		WebUtils.issueRedirect(request, response, kickoutUrl);
		return false;
	}

	private void out(ServletResponse hresponse, R r)
			throws IOException {
		try {
			hresponse.setCharacterEncoding("UTF-8");
			PrintWriter out = hresponse.getWriter();
			out.println(JSON.toJSONString(r));
			out.flush();
			out.close();
		} catch (Exception e) {
			System.err.println("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
		}
	}


}

