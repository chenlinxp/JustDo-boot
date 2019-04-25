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

	/**
	 * 获取实体角色数量
	 * @param map
	 * @return
	 */
	int count(Map<String,Object> map);

	/**
	 * 保存实体角色
	 * @param role
	 * @return
	 */
	int save(RoleDO role);

	/**
	 * 更新角色
	 * @param role
	 * @return
	 */
	int update(RoleDO role);

	/**
	 * 删除实体角色
	 * @param roleId
	 * @return
	 */
	int del(String roleId);

	/**
	 * 批量删除角色实体
	 * @param roleIds
	 * @return
	 */
	int batchDel(String[] roleIds);

	/**
	 * 通过角色IDs获取角色list
	 * @param roleIds
	 * @return
	 */
	List<RoleDO> getListByIds(String[] roleIds);
}
