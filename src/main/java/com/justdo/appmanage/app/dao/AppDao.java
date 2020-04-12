package com.justdo.appmanage.app.dao;

import com.justdo.appmanage.app.domain.AppDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * APP包管理
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-15 20:17:22
 */
@Mapper
public interface AppDao {

	/**
	* 返回实体
	* @param appId
	* @return AppDO
	*/
	AppDO get(String appId);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<AppDO> list(Map<String, Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存实体
	 * @param app
	 * @return
	 */
	int save(AppDO app);

	/**
	 * 更新实体
	 * @param app
	 * @return list
	 */
	int update(AppDO app);

	/**
	 * 删除实体
	 * @param appId
	 * @return list
	 */
	int del(String appId);

	/**
	 * 批量删除实体
	 * @param appIds
	 * @return list
	 */
	int batchDel(String[] appIds);

	/**
	 * 返回实体
	 * @param map  String bundleId ,int appType
	 * @return AppDO
	 */
	AppDO getByBundleId(Map<String, Object> map);

	/**
	 * 返回实体
	 * @param combineAppId
	 * @return AppDO
	 */
	AppDO getByCombineId(String combineAppId);
}
