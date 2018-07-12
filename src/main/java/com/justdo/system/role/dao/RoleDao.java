package com.justdo.system.role.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.justdo.system.role.domain.RoleDO;



/**
 * 角色
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface RoleDao {

	RoleDO get(Long roleId);
	
	List<RoleDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(RoleDO role);
	
	int update(RoleDO role);
	
	int remove(Long roleId);
	
	int batchDel(Long[] roleIds);
}
