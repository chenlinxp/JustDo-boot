package com.justdo.system.area.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.justdo.system.area.domain.AreaDO;

/**
 * 地区编码表
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:33:49
 */
@Mapper
public interface AreaDao {

	AreaDO get(String areaid);
	
	List<AreaDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AreaDO area);
	
	int update(AreaDO area);
	
	int del(String AREAID);
	
	int batchDel(String[] areaids);
}
