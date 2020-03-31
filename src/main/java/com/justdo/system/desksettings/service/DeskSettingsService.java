package com.justdo.system.desksettings.service;

import com.justdo.system.desksettings.domain.DeskSettingsDO;

import java.util.List;
import java.util.Map;

/**
 * 桌面设置
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-11-06 12:17:25
 */
public interface DeskSettingsService {

	/**
	 * 返回实体
	 * @param settingsId
	 * @return DeskSettingsDO
	 */
		DeskSettingsDO get(String settingsId);

	/**
	 * 返回实体
	 * @param EmployeeId
	 * @return DeskSettingsDO
	 */
	DeskSettingsDO getByEmployeeId(String EmployeeId);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<DeskSettingsDO> list(Map<String, Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存实体
	 * @param deskSettings
	 * @return
	 */
	int save(DeskSettingsDO deskSettings);

	/**
	 * 更新实体
	 * @param deskSettings
	 * @return list
	 */
	int update(DeskSettingsDO deskSettings);

	/**
	 * 删除实体
	 * @param settingsId
	 * @return list
	 */
	int del(String settingsId);

	/**
	 * 批量删除实体
	 * @param settingsIds
	 * @return list
	 */
	int batchDel(String[] settingsIds);

}
