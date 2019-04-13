package com.justdo.system.role.service;

import com.justdo.system.role.domain.RoleDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 员工角色
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public interface RoleService {

	RoleDO get(String roleId);

	List<RoleDO> list();

	int save(RoleDO role);

	int update(RoleDO role);

	int del(String roleId);

	List<RoleDO> list(String employeeId);

	int batchDel(String[] ids);
}
