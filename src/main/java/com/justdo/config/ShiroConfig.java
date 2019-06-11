package com.justdo.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.justdo.common.redis.shiro.RedisCacheManager;
import com.justdo.common.redis.shiro.RedisSessionDAO;
import com.justdo.system.employee.shiro.EmployeeRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;


/**
 * @author justdo
 */
@Configuration
public class ShiroConfig {
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
	 *
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
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/403");


		//oauth过滤
//		Map<String, Filter> filters = new HashMap<>();
//		filters.put("oauth2", new OAuth2Filter());
//		shiroFilterFactoryBean.setFilters(filters);
		//配置过滤器anon(匿名不被拦截)，authcBasic，auchc，user是认证过滤器，perms，roles，ssl，rest，port是授权过滤器
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/css/**", "anon");
		filterChainDefinitionMap.put("/js/**", "anon");
		filterChainDefinitionMap.put("/fonts/**", "anon");
		filterChainDefinitionMap.put("/img/**", "anon");
		filterChainDefinitionMap.put("/docs/**", "anon");
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/upload/**", "anon");
		filterChainDefinitionMap.put("/files/**", "anon");
		filterChainDefinitionMap.put("/", "anon");
		filterChainDefinitionMap.put("/blog", "anon");
		filterChainDefinitionMap.put("/blog/open/**", "anon");
		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}
	/*
	安全管理器
	 */
	@Bean
	public SecurityManager securityManager(RedisTemplate redisTemplate) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		//设置realm.  securityManager.setRealm(employeeRealm(redisTemplate));
		securityManager.setRealm(employeeRealm());
		// 自定义缓存实现 使用redis
		securityManager.setCacheManager(redisCacheManager(redisTemplate));
        //session 管理 使用redis
		securityManager.setSessionManager(sessionManager(redisTemplate));
        //cookie管理器，注入记住我管理器;
		securityManager.setRememberMeManager(rememberMeManager());

		return securityManager;
	}

	@Bean
	EmployeeRealm employeeRealm() {
		EmployeeRealm employeeRealm = new EmployeeRealm();
	//	employeeRealm.setCredentialsMatcher(credentialsMatcher());
		return employeeRealm;
	}

	/**
	 * 开启shiro aop注解支持.
	 * 使用代理方式;所以需要开启代码支持;
	 *
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 缓存管理器的配置
	 * @param redisTemplate
	 * @return
	 */
	@Bean(name = "RedisCacheManager")
	public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
		RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
		//name是key的前缀，可以设置任何值，无影响，可以设置带项目特色的值
		redisCacheManager.createCache("shiro_redis");
		return redisCacheManager;
	}

	/**
	 * RedisSessionDAO sessionDao层的实现
	 */
	@Bean
	public RedisSessionDAO redisSessionDAO(RedisTemplate redisTemplate) {
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO(redisTemplate);
		return redisSessionDAO;
	}

	/**
	 *  配置sessionmanager，由redis存储数据
	 */
	@Bean
	public DefaultWebSessionManager sessionManager(RedisTemplate redisTemplate) {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//		//这个name的作用也不大，只是有特色的cookie的名称。
//		redisSessionDao.setSessionIdGenerator(sessionIdGenerator("startCookie"));
		sessionManager.setSessionDAO(redisSessionDAO(redisTemplate));
		sessionManager.setDeleteInvalidSessions(true);
		SimpleCookie cookie = new SimpleCookie();
		cookie.setName("startCookie");
		sessionManager.setSessionIdCookie(cookie);
		sessionManager.setSessionIdCookieEnabled(true);

		Collection<SessionListener> listeners = new ArrayList<SessionListener>();
		listeners.add(new BDSessionListener());
		sessionManager.setSessionListeners(listeners);
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
	public SimpleCookie rememberMeCookie(){
		SimpleCookie rememberMeCookie = new SimpleCookie("rememberMe");
		rememberMeCookie.setHttpOnly(true);
		//<!-- 记住我cookie生效时间7天 ,单位秒;-->
		rememberMeCookie.setMaxAge(604800);
		return rememberMeCookie;
	}

	/**
	 * cookie管理对象;记住我功能
	 * @return
	 */
	public CookieRememberMeManager rememberMeManager(){
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		//rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
		cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
		return cookieRememberMeManager;
    }

	/**
	 * realm的认证算法
	 * @return
	 */
	@Bean(name = "hashedCredentialsMatcher")
	public HashedCredentialsMatcher credentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("md5");
		//2次迭代
		credentialsMatcher.setHashIterations(2);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}

}
