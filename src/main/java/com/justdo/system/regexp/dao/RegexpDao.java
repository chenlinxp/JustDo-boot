package com.justdo.system.regexp.dao;

import com.justdo.system.regexp.domain.RegexpDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.justdo.system.regexp.domain.RegexpEXDO;

/**
 * 系统正则表达式
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-06-26 13:13:39
 */
@Mapper
public interface RegexpDao {

	RegexpDO get(String rid);
	
	List<RegexpDO> list(Map<String,Object> map);


	List<RegexpEXDO> listEX(Map<String,Object> map);

	
	int count(Map<String,Object> map);
	
	int save(RegexpDO regexp);
	
	int update(RegexpDO regexp);
	
	int del(String RID);
	
	int batchDel(String[] rids);
}
