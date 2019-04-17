package com.justdo.system.resource.service;

import com.justdo.common.domain.Tree;
import com.justdo.system.resource.domain.ResourceDO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 资源菜单管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-18 22:48:34
 */
public interface ResourceService {

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
	 * 根据员工ID获取资源树
	 * @param employeeId
	 * @return
	 */
	Tree<ResourceDO> getEmployeeResourceTree(String employeeId);
	/**
	 * 获取资源树
	 * @return
	 */
	Tree<ResourceDO> getTree();

	/**
	 * 根据角色ID获取资源树
	 * @param roleId
	 * @return
	 */
	Tree<ResourceDO> getTree(String roleId);

	/**
	 * 根据员工ID获取权限列表
	 * @param employeeId
	 * @return
	 */
	Set<String> listEmployeePermissions(String employeeId);


	/**
	 * 根据员工ID获取资源树列
	 * @param employeeId
	 * @return
	 */
	List<Tree<ResourceDO>> listEmployeeResourceTree(String employeeId);
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
