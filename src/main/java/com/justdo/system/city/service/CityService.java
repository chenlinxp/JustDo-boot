package com.justdo.system.city.service;

import com.justdo.system.city.domain.CityDO;

import java.util.List;
import java.util.Map;

/**
 * 城市编码信息
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:33:37
 */
public interface CityService {
	
	CityDO get(String cityid);
	
	List<CityDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CityDO city);
	
	int update(CityDO city);
	
	int del(String cityid);
	
	int batchDel(String[] cityids);
}
