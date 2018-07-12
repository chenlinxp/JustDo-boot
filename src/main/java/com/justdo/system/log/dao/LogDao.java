package com.justdo.system.log.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.justdo.system.log.domain.LogDO;
/**
 * 系统日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface LogDao {

	LogDO get(Long id);
	
	List<LogDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(LogDO log);
	
	int update(LogDO log);
	
	int remove(Long id);
	
	int batchDel(Long[] ids);
}
