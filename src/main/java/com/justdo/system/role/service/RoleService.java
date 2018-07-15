package com.justdo.system.role.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.justdo.system.role.domain.RoleDO;

/**
 * 角色
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public interface RoleService {

	RoleDO get(String id);

	List<RoleDO> list();

	int save(RoleDO role);

	int update(RoleDO role);

	int del(String id);

	List<RoleDO> list(String userId);

	int batchDel(String[] ids);
}
