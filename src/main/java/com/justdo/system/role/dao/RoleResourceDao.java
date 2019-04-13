package com.justdo.system.role.dao;

import com.justdo.system.role.domain.RoleResourceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色与资源菜单对应关系
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-18 22:48:34
 */
@Mapper
public interface RoleResourceDao {

	/**
	* 返回实体
	* @param roleResourceId
	* @return RoleResourceDO
	*/
	RoleResourceDO get(Long roleResourceId);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<RoleResourceDO> list(Map<String, Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存实体
	 * @param roleResource
	 * @return
	 */
	int save(RoleResourceDO roleResource);

	/**
	 * 更新实体
	 * @param roleResource
	 * @return list
	 */
	int update(RoleResourceDO roleResource);

	/**
	 * 删除实体
	 * @param roleResourceId
	 * @return list
	 */
	int del(Long roleResourceId);

	/**
	 * 批量删除实体
	 * @param roleResourceIds
	 * @return list
	 */
	int batchDel(Long[] roleResourceIds);

	/**
	 *
	 * @param roleId
	 * @return
	 */
	List<String> listResourceIdByRoleId(String roleId);

	/**
	 *
	 * @param roleId
	 * @return
	 */
	int delByRoleId(String roleId);

	/**
	 *
	 * @param resourceId
	 * @return
	 */
	int delByResourceId(String resourceId);

	/**
	 *
	 * @param list
	 * @return
	 */
	int batchSave(List<RoleResourceDO> list);
}
