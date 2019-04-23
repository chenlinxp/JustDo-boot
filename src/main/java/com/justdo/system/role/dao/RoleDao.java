package com.justdo.system.role.dao;

import com.justdo.system.role.domain.RoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;



/**
 * 角色
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface RoleDao {

	RoleDO get(String roleId);
	
	List<RoleDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(RoleDO role);
	
	int update(RoleDO role);
	
	int del(String roleId);
	
	int batchDel(String[] roleIds);

	List<RoleDO> getListByIds(String[] roleIds);
}
