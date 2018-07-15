package com.justdo.system.user.dao;

import com.justdo.system.user.domain.UserRoleDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户与角色对应关系
 * 
 * @author chenlin
 * @email
 * @date 2017-10-03 11:08:59
 */
@Mapper
public interface UserRoleDao {

	UserRoleDO get(String id);

	List<UserRoleDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(UserRoleDO userRole);

	int update(UserRoleDO userRole);

	int del(String id);

	int batchDel(String[] ids);

	List<String> listRoleId(String userId);

	int delByUserId(String userId);

	int delByRoleId(String roleId);

	int batchSave(List<UserRoleDO> list);

	int batchDelByUserId(String[] ids);
}
