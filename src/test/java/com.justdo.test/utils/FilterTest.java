package com.justdo.test.utils;

import com.justdo.system.permissioninit.domain.PermissionInitDO;
import com.justdo.system.permissioninit.service.PermissionInitService;
import com.justdo.test.JustdoApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/23 下午5:45
 */
public class FilterTest extends JustdoApplicationTest {

	@Autowired
    private PermissionInitService permissionInitService;

	@Test
	public void testPermissionInit() {
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
		//从数据库获取
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sort", "PERMISSION_SORT");
		map.put("ORDER", "ASC");
		List<PermissionInitDO> list = permissionInitService.list(map);
		for (PermissionInitDO permissionInit : list) {
			filterChainDefinitionMap.put(permissionInit.getPermissionUrl(),
					permissionInit.getPermissionInit());
		}
		//filterChainDefinitionMap = shiroService.loadFilterChainDefinitions();
		//shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		System.out.println("---------");
	}
}
