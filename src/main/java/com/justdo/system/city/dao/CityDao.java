package com.justdo.system.city.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.justdo.system.city.domain.CityDO;

/**
 * 城市编码信息
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:33:37
 */
@Mapper
public interface CityDao {

	CityDO get(String cityid);
	
	List<CityDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(CityDO city);
	
	int update(CityDO city);
	
	int del(String CITYID);
	
	int batchDel(String[] cityids);
}
