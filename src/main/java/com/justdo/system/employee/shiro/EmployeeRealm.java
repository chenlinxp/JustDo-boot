package com.justdo.system.employee.shiro;


import com.justdo.common.utils.ShiroUtils;
import com.justdo.common.utils.ApplicationContextUtils;
import com.justdo.system.employee.dao.EmployeeDao;
import com.justdo.system.employee.domain.EmployeeDO;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import com.justdo.system.resource.service.ResourceService;
import com.justdo.system.role.domain.RoleDO;
import com.justdo.system.role.service.RoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 员工账号认证、权限授权
 *
 * @author: chenlin
 * @date:2019-06-12
 */
public class EmployeeRealm extends AuthorizingRealm {


//	@Autowired
//	private ShiroService shiroService;

//	@Override
//	public boolean supports(AuthenticationToken token) {
//		return token instanceof OAuth2Token;
//	}

	/**AuthorizingRealm
	 * 授权：即权限验证，验证某个已认证的用户是否拥有某个权限；即判断用户是否能做事情，常见的如：验证某个用户是否拥有某个角色。或者细粒度的验证某个用户对某个资源是否具有某个权限
	 * @param principalCollection
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

		String employeeId = ShiroUtils.getEmployeeId();
		//String username = (String)principalCollection.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

//		Set<String> perms = resourceService.listEmployeePermissions(employeeId);
//		authorizationInfo.setStringPermissions(perms);

		//角色获取
		RoleService roleService = ApplicationContextUtils.getBean(RoleService.class);
		List<RoleDO> roles = roleService.list(employeeId);

		for (RoleDO role : roles) {
			authorizationInfo.addRole(role.getRoleName());
			//权限获取
			ResourceService resourceService = ApplicationContextUtils.getBean(ResourceService.class);
			Set<String> perms = resourceService.listEmployeePermissions(role.getRoleId());

			for (String perm : perms) {
				authorizationInfo.addStringPermission(perm);
			}
		}
		return authorizationInfo;
	}

	/**
	 * 认证：身份认证/登录，验证用户是不是拥有相应的身份
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

//		UsernamePasswordToken atoken = (UsernamePasswordToken)token;
//		String username = atoken.getUsername();4d9573ec9f4cf8978543486c9e9eb681
//		String password = new String((char[]) token.getCredentials());

		String loginName = (String) token.getPrincipal();
		Map<String, Object> map = new HashMap<>(16);
		map.put("loginName", loginName);
		EmployeeDao employeeDao = ApplicationContextUtils.getBean(EmployeeDao.class);
		// 查询用户信息
		EmployeeDO employee = employeeDao.list(map).get(0);
		String password = employee.getPassword();
		SimpleEmployeeDO simpleEmployeeDO = new SimpleEmployeeDO();
		simpleEmployeeDO.setEmployeeId(employee.getEmployeeId());
		simpleEmployeeDO.setLoginName(employee.getLoginName());
		simpleEmployeeDO.setEmployeeNumber(employee.getEmployeeNumber());
		simpleEmployeeDO.setDeptmentId(employee.getDeptmentId());
		simpleEmployeeDO.setOrganId(employee.getOrganId());
		simpleEmployeeDO.setPositionId(employee.getPositionId());
		simpleEmployeeDO.setPhotoUrl(employee.getPhotoUrl());
		String salt = employee.getPasswordSalt();
		// 账号不存在
		if (employee == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		// 账号锁定
		if (employee.getEmployeeState() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		employee = null;
		//ByteSource.Util.bytes(salt)，简单讲解一下这里，
		//对于第一次接触shiro的人来说应该是最难理解的地方，这个SimpleAuthenticationInfo会将你Token中的账号密码通过getName（）
		//这个方法获取，与你传入的username及password进行对比，byteSource是盐值，
		//是为了加密时使用的。这里我采用了盐值存储在用户信息中的方式，而盐值的设置是在
		//我注册用户时设置的，我采用的是随机字符串形式，当然你也可以采用随机数格式。
		//而这个解密的方式在哪配置的，请看后面。
        //以下信息是从数据库中获取的

//		1)principal：认证的实体信息，可以是username，也可以是数据库表对应的用户的实体对象
		Object principal = simpleEmployeeDO;
		//2)credentials：密码
		Object credentials = password;
		//3)realmName：当前realm对象的name，调用父类的getName()方法即可
		String realmName = getName();
		//4)credentialsSalt盐值
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, ByteSource.Util.bytes(salt), realmName);
		return info;
	}

	/**
	 * 重写方法,清除当前用户的的 授权缓存
	 * @param principals
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 重写方法，清除当前用户的 认证缓存
	 * @param principals
	 */
	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	/**
	 * 自定义方法：清除所有 授权缓存
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	/**
	 * 自定义方法：清除所有 认证缓存
	 */
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	/**
	 * 自定义方法：清除所有的  认证缓存  和 授权缓存
	 */
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}
}
