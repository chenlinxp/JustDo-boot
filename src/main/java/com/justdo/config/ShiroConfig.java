package com.justdo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.justdo.common.filter.KickoutSessionControlFilter;
import com.justdo.common.filter.OAuth2Filter;
import com.justdo.common.redis.RedisManager;
import com.justdo.common.redis.shiro.RedisCacheManager;
import com.justdo.common.redis.shiro.RedisSessionDAO;
import com.justdo.system.employee.shiro.EmployeeRealm;
import com.justdo.system.employee.shiro.RetryLimitHashedCredentialsMatcher;
import com.justdo.system.permissioninit.domain.PermissionInitDO;
import com.justdo.system.permissioninit.service.PermissionInitService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;


/**
 * Shiro配置管理
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:36:21
 */
@Configuration
public class ShiroConfig {

	@Autowired
	PermissionInitService permissionInitService;

	@Value("${spring.redis.host}")
	private String host;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.port}")
	private int port;
	@Value("${spring.redis.timeout}")
	private int timeout;
	@Value("${server.session-timeout}")
	private int tomcatTimeout;

	@Bean
	public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
	 * @return
	 */
	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	@Bean
	ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		shiroFilterFactoryBean.setLoginUrl("/justdo/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");


		//自定义拦截器
		Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
		//限制同一帐号同时在线的个数。
		filtersMap.put("kickout", kickoutSessionControlFilter());

		//oauth过滤
		filtersMap.put("oauth2", new OAuth2Filter());

		shiroFilterFactoryBean.setFilters(filtersMap);

		//配置过滤器anon(匿名不被拦截)，authcBasic，authc，user是认证过滤器，perms，roles，ssl，rest，port是授权过滤器
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		//开放登陆接口
//		filterChainDefinitionMap.put("/css/**", "anon,kickout");
//		filterChainDefinitionMap.put("/js/**", "anon,kickout");
//		filterChainDefinitionMap.put("/fonts/**", "anon,kickout");
//		filterChainDefinitionMap.put("/img/**", "anon,kickout");
//		filterChainDefinitionMap.put("/docs/**", "anon,kickout");
//		filterChainDefinitionMap.put("/druid/**", "anon,kickout");
//		filterChainDefinitionMap.put("/upload/**", "anon,kickout");
//		filterChainDefinitionMap.put("/files/**", "anon,kickout");
//		filterChainDefinitionMap.put("/verification", "anon,kickout");
//		filterChainDefinitionMap.put("/portal", "anon");
//		filterChainDefinitionMap.put("/portal/open/**", "anon");
//		filterChainDefinitionMap.put("/login", "anon,kickout");
//		filterChainDefinitionMap.put("/index", "user,kickout");
//		filterChainDefinitionMap.put("/logout", "anon");
//		//filterChainDefinitionMap.put("/**", "user");
//		//其余接口一律拦截
//		//主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
//		filterChainDefinitionMap.put("/**", "user,kickout");
		//filterChainDefinitionMap.put("/app/**", "oauth2");
		// 配置不会被拦截的链接 顺序判断
		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		// 从数据库获取动态的权限
		// filterChainDefinitionMap.put("/add", "perms[权限添加]");
		// <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
		// <!-- authc:所有url都必须认证通过才可以访问; user:表示配置记住我或认证通过可以访问; anon:所有url都都可以匿名访问-->
		//logout这个拦截器是shiro已经实现好了的。
		//如果开启限制同一账号登录,改为 .put("/**", "user,kickout");
		// 从数据库获取
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sort","PERMISSION_SORT");
		map.put("ORDER","ASC");
		List<PermissionInitDO> list = permissionInitService.list(map);
		for (PermissionInitDO permissionInit : list) {
			filterChainDefinitionMap.put(permissionInit.getPermissionUrl(),
					permissionInit.getPermissionInit());
		}
		//filterChainDefinitionMap = shiroService.loadFilterChainDefinitions();
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	/*
	安全管理器
	 */
	@Bean
	public SecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//设置realm.
		securityManager.setRealm(employeeRealm());
		//自定义缓存实现 使用redis
		securityManager.setCacheManager(cacheManager());
		//session 管理
		securityManager.setSessionManager(sessionManager());

		//cookie管理器
		securityManager.setRememberMeManager(rememberMeManager());

		return securityManager;
	}

	@Bean
	EmployeeRealm employeeRealm() {
		EmployeeRealm employeeRealm = new EmployeeRealm();

		//employeeRealm.setCachingEnabled(true);

		//启用授权缓存，即缓存AuthorizationInfo信息，默认false
		employeeRealm.setAuthorizationCachingEnabled(false);

		//启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
		employeeRealm.setAuthenticationCachingEnabled(false);

		//配置自定义密码比较器
		employeeRealm.setCredentialsMatcher(credentialsMatcher());

		return employeeRealm;

	}

	/**
	 * 开启shiro aop注解支持.
	 * 使用代理方式;所以需要开启代码支持;
	 *
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 配置shiro redisManager
	 * @return
	 */
	@Bean
	public RedisManager redisManager() {
		RedisManager redisManager = new RedisManager();
		redisManager.setHost(host);
		redisManager.setPort(port);
		redisManager.setExpire(1800);// 配置缓存过期时间
		redisManager.setTimeout(60000);
		redisManager.setPassword(password);
		return redisManager;
	}

	/**
	 * cacheManager 缓存 redis实现
	 * 缓存管理器
	 * @return
	 */
	@Bean(name = "cacheManager")
	public RedisCacheManager cacheManager() {
		RedisCacheManager redisCacheManager = new RedisCacheManager();
		redisCacheManager.setRedisManager(redisManager());
		return redisCacheManager;
	}

	/**
	 * RedisSessionDAO sessionDao层的实现
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO() {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager());
		return redisSessionDAO;
	}

	/**
	 * 配置session监听
	 * @return
	 */
	@Bean(name = "sessionListener")
	public ShiroSessionListener sessionListener(){
		ShiroSessionListener sessionListener = new ShiroSessionListener();
		return sessionListener;
	}

	/**
	 *  session的管理
	 */
	@Bean
	public DefaultWebSessionManager sessionManager() {

		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
		listeners.add(sessionListener());

		//全局会话超时时间（单位毫秒），默认30分钟
		sessionManager.setGlobalSessionTimeout(tomcatTimeout * 1000);
		sessionManager.setSessionDAO(redisSessionDAO());
		sessionManager.setSessionListeners(listeners);
//		sessionManager.setCacheManager(ehCacheManager());
//		sessionManager.setSessionIdCookieEnabled(true);
// 		sessionManager.setSessionIdCookie(rememberMeCookie());
		//是否开启删除无效的session对象  默认为true
		sessionManager.setDeleteInvalidSessions(true);
		//是否开启定时调度器进行检测过期session 默认为true
		sessionManager.setSessionValidationSchedulerEnabled(true);
		//设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
		//设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
		sessionManager.setSessionValidationInterval(3600000);
		//取消url 后面的 JSESSIONID
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		return sessionManager;

	}


	/**
	 * 手动指定cookie
	 * 这个参数是RememberMecookie的名称，随便起。
	 * remenberMeCookie是一个实现了将用户名保存在客户端的一个cookie，与登陆时的cookie是两个simpleCookie。
	 * 登陆时会根据权限去匹配，如是user权限，则不会先去认证模块认证，而是先去搜索cookie中是否有rememberMeCookie，
	 * 如果存在该cookie，则可以绕过认证模块，直接寻找授权模块获取角色权限信息。
	 * 如果权限是authc,则仍会跳转到登陆页面去进行登陆认证.
	 * @return
	 */
	@Bean(name = "rememberMeCookie")
	public SimpleCookie rememberMeCookie(){
		SimpleCookie rememberMeCookie = new SimpleCookie("rememberMe");
		//设为true后，只能通过http访问，javascript无法访问
		//防止xss读取cookie
		/*保证该系统不会受到跨域的脚本操作供给*/
		rememberMeCookie.setHttpOnly(true);
		//<!-- 记住我cookie生效时间7天 ,单位秒;-->
		//maxAge=-1表示浏览器关闭时失效此Cookie
		rememberMeCookie.setMaxAge(604800);
		rememberMeCookie.setPath("/justdo/login");
		rememberMeCookie.setDomain("");

		return rememberMeCookie;


	}

	/**
	 * cookie管理对象;记住我功能
	 * @return
	 */
	@Bean(name = "rememberMeManager")
	public CookieRememberMeManager rememberMeManager(){

		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();

		cookieRememberMeManager.setCookie(rememberMeCookie());
		//rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));

		return cookieRememberMeManager;
	}

	/**
	 * FormAuthenticationFilter 过滤器 过滤记住我
	 * @return
	 */
	@Bean
	public FormAuthenticationFilter formAuthenticationFilter(){
		FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
		//对应前端的checkbox的name = rememberme
		formAuthenticationFilter.setRememberMeParam("rememberMe");
		return formAuthenticationFilter;
	}

	/**
	 * realm的认证算法
	 * @return
	 */
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher credentialsMatcher() {
		RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName("md5");
		//2次迭代
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}

	/**
	 * 限制同一账号登录同时登录人数控制
	 * @return
	 */
	@Bean
	public KickoutSessionControlFilter kickoutSessionControlFilter(){

		KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
		//使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
		//这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
		//也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
		kickoutSessionControlFilter.setCacheManager(cacheManager());
		//用于根据会话ID，获取会话进行踢出操作的；
		kickoutSessionControlFilter.setSessionManager(sessionManager());
		//是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
		kickoutSessionControlFilter.setKickoutAfter(false);
		//同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
		kickoutSessionControlFilter.setMaxSession(1);
		//被踢出后重定向到的地址；
		kickoutSessionControlFilter.setKickoutUrl("/logout");
		return kickoutSessionControlFilter;
	}
}
