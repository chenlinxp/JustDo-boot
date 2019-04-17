package com.justdo.system.employee.shiro;


import com.justdo.common.utils.ShiroUtils;
import com.justdo.config.ApplicationContextRegister;
import com.justdo.system.employee.dao.EmployeeDao;
import com.justdo.system.employee.domain.EmployeeDO;
import com.justdo.system.resource.service.ResourceService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 *员工认证、授权
 *
 */
public class EmployeeRealm extends AuthorizingRealm {


	/**
	 * 授权：即权限验证，验证某个已认证的用户是否拥有某个权限；即判断用户是否能做事情，常见的如：验证某个用户是否拥有某个角色。或者细粒度的验证某个用户对某个资源是否具有某个权限
	 * @param arg0
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		String employeeId = ShiroUtils.getEmployeeId();
		ResourceService resourceService = ApplicationContextRegister.getBean(ResourceService.class);
		Set<String> perms = resourceService.listEmployeePermissions(employeeId);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	/**
	 * 认证：身份认证/登录，验证用户是不是拥有相应的身份
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String loginName = (String) token.getPrincipal();
		Map<String, Object> map = new HashMap<>(16);
		map.put("loginName", loginName);

		String password = new String((char[]) token.getCredentials());

		EmployeeDao employeeDao = ApplicationContextRegister.getBean(EmployeeDao.class);
		// 查询用户信息
		EmployeeDO employee = employeeDao.list(map).get(0);

		// 账号不存在
		if (employee == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}

		// 密码错误
		if (!password.equals(employee.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		// 账号锁定
		if (employee.getEmployeeState() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(employee, password, getName());
		return info;
	}

}
