package com.justdo.system.area.service;

import com.justdo.system.area.domain.AreaDO;

import java.util.List;
import java.util.Map;

/**
 * 地区编码表
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:33:49
 */
public interface AreaService {
	
	AreaDO get(String areaid);
	
	List<AreaDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AreaDO area);
	
	int update(AreaDO area);
	
	int del(String areaid);
	
	int batchDel(String[] areaids);
}
