package com.justdo.system.role.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.justdo.system.role.domain.RoleMenuDO;
/**
 * 角色与菜单对应关系
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface RoleMenuDao {

	RoleMenuDO get(String id);
	
	List<RoleMenuDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(RoleMenuDO roleMenu);
	
	int update(RoleMenuDO roleMenu);
	
	int del(String id);
	
	int batchDel(String[] ids);
	
	List<String> listMenuIdByRoleId(String roleId);
	
	int delByRoleId(String roleId);

	int delByMenuId(String menuId);
	
	int batchSave(List<RoleMenuDO> list);
}
