package com.justdo.system.regexp.service;

import com.justdo.system.regexp.domain.RegexpDO;
import com.justdo.system.regexp.domain.RegexpEXDO;

import java.util.List;
import java.util.Map;

/**
 * 系统正则表达式
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-06-26 13:13:39
 */
public interface RegexpService {
	
	RegexpDO get(String rid);
	
	List<RegexpDO> list(Map<String, Object> map);

	List<RegexpEXDO> listEX(Map<String,Object> map);
	
	int count(Map<String, Object> map);
	
	int save(RegexpDO regexp);
	
	int update(RegexpDO regexp);
	
	int remove(String rid);
	
	int batchDel(String[] rids);
}
