package com.justdo.appmanage.appversion.dao;

import com.justdo.appmanage.appversion.domain.AppVersionDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * APP包版本记录管理
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-18 13:10:53
 */
@Mapper
public interface AppVersionDao {

	/**
	* 返回实体
	* @param appVersionId
	* @return AppVersionDO
	*/
	AppVersionDO get(String appVersionId);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<AppVersionDO> list(Map<String,Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String,Object> map);

	/**
	 * 保存实体
	 * @param appVersion
	 * @return
	 */
	int save(AppVersionDO appVersion);

	/**
	 * 更新实体
	 * @param appVersion
	 * @return list
	 */
	int update(AppVersionDO appVersion);

	/**
	 * 删除实体
	 * @param appVersionId
	 * @return list
	 */
	int del(String appVersionId);

	/**
	 * 批量删除实体
	 * @param appVersionIds
	 * @return list
	 */
	int batchDel(String[] appVersionIds);
}
