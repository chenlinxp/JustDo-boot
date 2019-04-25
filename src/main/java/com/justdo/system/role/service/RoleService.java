package com.justdo.system.role.service;

import com.justdo.system.role.domain.RoleDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 员工角色
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public interface RoleService {

	/**
	 * 获取实体角色
	 * @param roleId
	 * @return
	 */
	RoleDO get(String roleId);

	/**
	 * 获取实体角色list
	 * @param map
	 * @return
	 */
	List<RoleDO> list(Map<String,Object> map);

	int save(RoleDO role);

	int update(RoleDO role);

	int del(String roleId);

	List<RoleDO> list(String employeeId);

	List<RoleDO> list(List<String> roleIds);

	int batchDel(String[] ids);
}
