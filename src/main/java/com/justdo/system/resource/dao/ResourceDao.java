package com.justdo.system.resource.dao;

import com.justdo.system.resource.domain.ResourceDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 资源菜单管理
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-18 22:48:34
 */
@Mapper
public interface ResourceDao {

	/**
	* 返回实体
	* @param resourceId
	* @return ResourceDO
	*/
	ResourceDO get(String resourceId);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<ResourceDO> list(Map<String, Object> map);

	/**
	 * 根据员工ID获取资源集合
	 * @param employeeId
	 * @return
	 */
	List<String> listEmployeePermissions(String employeeId);

	/**
	 * 根据角色ID获取资源集合
	 * @param roleId
	 * @return
	 */
	List<String> listEmployeePermissions2(String roleId);
	/**
	 * 根据员工ID获取资源列
	 * @param employeeId
	 * @return
	 */
	List<ResourceDO> listResourceByEmployeeId(String employeeId);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存实体
	 * @param resource
	 * @return
	 */
	int save(ResourceDO resource);

	/**
	 * 更新实体
	 * @param resource
	 * @return list
	 */
	int update(ResourceDO resource);

	/**
	 * 删除实体
	 * @param resourceId
	 * @return list
	 */
	int del(String resourceId);

	/**
	 * 批量删除实体
	 * @param resourceIds
	 * @return list
	 */
	int batchDel(String[] resourceIds);


}
