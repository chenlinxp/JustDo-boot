package com.justdo.system.permissioninit.dao;

import com.justdo.system.permissioninit.domain.PermissionInitDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * shiro初始权限
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-06-27 12:44:13
 */
@Mapper
public interface PermissionInitDao {

	/**
	* 返回实体
	* @param permissionId
	* @return PermissionInitDO
	*/
	PermissionInitDO get(String permissionId);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<PermissionInitDO> list(Map<String, Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存实体
	 * @param permissionInit
	 * @return
	 */
	int save(PermissionInitDO permissionInit);

	/**
	 * 更新实体
	 * @param permissionInit
	 * @return list
	 */
	int update(PermissionInitDO permissionInit);

	/**
	 * 删除实体
	 * @param permissionId
	 * @return list
	 */
	int del(String permissionId);

	/**
	 * 批量删除实体
	 * @param permissionIds
	 * @return list
	 */
	int batchDel(String[] permissionIds);
}
