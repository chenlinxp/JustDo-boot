package com.justdo.common.filter;

import com.alibaba.fastjson.JSON;
import com.justdo.common.redis.shiro.RedisCacheManager;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

/**import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
 * @author justdo
 * @date
 * 思路：
 * 1.读取当前登录用户名，获取在缓存中的sessionId队列
 * 2.判断队列的长度，大于最大登录限制的时候，按踢出规则
 *  将之前的sessionId中的session域中存入kickout：true，并更新队列缓存
 * 3.判断当前登录的session域中的kickout如果为true，
 * 想将其做退出登录处理，然后再重定向到踢出登录提示页面
 */
public class KickoutSessionControlFilter extends AccessControlFilter {


	private String kickoutUrl; //踢出后到的地址

	private boolean kickoutAfter = false; //踢出之前登录的/之后登录的用户 默认踢出之前登录的用户

	private int maxSession = 1; //同一个帐号最大会话数 默认1

	/**
	 * The Redis key prefix for caches
	 */
	private String keyPrefix = "shiro_redis_cache:";

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

	//设置Cache的key的前缀
	public void setCacheManager(RedisCacheManager cacheManager) {
		redisCacheManager = cacheManager;
		redisCacheManager.setKeyPrefix(keyPrefix);
		this.cache = redisCacheManager.getCache(keyPrefix);

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

		Subject subject = getSubject(request, response);

		if (!subject.isAuthenticated() && !subject.isRemembered()) {
			//如果没有登录，直接进行登录的流程
			return true;
		}

		Session session = subject.getSession();

		SimpleEmployeeDO simpleEmployeeDO = (SimpleEmployeeDO) subject.getPrincipal();

		String username = simpleEmployeeDO.getLoginName();

		Serializable sessionId = session.getId();

		//读取缓存   没有就存入
		Deque<Serializable> deque = cache.get(username);

		//如果此用户没有session队列，也就是还没有登录过，缓存中没有
		//就new一个空队列，不然deque对象为空，会报空指针
		if (deque == null || deque.size() == 0) {
			deque = new LinkedList<Serializable>();
			deque.push(sessionId);
			session.setAttribute("username", username);
			cache.put(username, deque);
		}
		//String name = String.valueOf(session.getAttribute("username"));
		session.setAttribute("kickout",true);
		//System.out.println(name);

		//如果队列里没有此sessionId，且用户没有被踢出；放入队列
		if (!deque.contains(sessionId)) {
			//将sessionId存入队列
			deque.addFirst(sessionId);
			//将用户的sessionId队列缓存
			cache.put(username, deque);
		}

		//如果队列里的sessionId数超出最大会话数，开始踢人
		Session kickoutSession = null;
		while (deque.size() > maxSession) {
			Serializable kickoutSessionId = null;
			kickoutSessionId = deque.removeLast();
			//踢出后再更新下缓存队列
			cache.put(username, deque);
			try {
				kickoutSession = sessionManager.getSessionDAO().readSession(kickoutSessionId);
				//获取被踢出的sessionId的session对象
				//kickoutSession = sessionManager.getSession(new DefaultSessionKey(kickoutSessionId));
				if (kickoutSession != null) {
					//sessionManager.getSessionDAO().delete(kickoutSession);
					//设置会话的kickout属性表示踢出了
					session.setAttribute("kickout", false);
				}
			} catch (Exception e) {//ignore exception
			}
		}

		//如果被踢出了，直接退出，重定向到踢出后的地址
//		System.out.println(session.getAttribute("kickout"));
		if (!((Boolean) session.getAttribute("kickout"))) {
//			Map<String, String> resultMap = new HashMap<String, String>();
//			//判断是不是Ajax请求
//			System.out.println(((HttpServletRequest) request).getHeader("X-Requested-With"));
//			if ("XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {
//				resultMap.put("user_status", "300");
//				resultMap.put("message", "您已经在其他地方登录，请重新登录！");
//				//输出json串
//				out(response, resultMap);
//				System.out.println("您已经在其他地方登录，请重新登录！");
//				return false;
//			}
			return false;
		}

		//如果被踢出了，直接退出，重定向到踢出后的地址
//		if ((Boolean)session.getAttribute("kickout")!=null&&(Boolean)session.getAttribute("kickout") == true) {
//			//会话被踢出了
//			try {
//				//退出登录
//				subject.logout();
//			} catch (Exception e) { //ignore
//			}
//			saveRequest(request);
//			//重定向
//			WebUtils.issueRedirect(request, response, kickoutUrl);
//			return false;
//		}
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

	private void out(ServletResponse hresponse, Map<String, String> resultMap)
			throws IOException {
		try {
			hresponse.setCharacterEncoding("UTF-8");
			PrintWriter out = hresponse.getWriter();
			out.println(JSON.toJSONString(resultMap));
			out.flush();
			out.close();
		} catch (Exception e) {
			System.err.println("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
		}
	}

}
