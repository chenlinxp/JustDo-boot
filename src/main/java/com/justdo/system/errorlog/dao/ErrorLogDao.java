package com.justdo.system.errorlog.dao;

import com.justdo.system.errorlog.domain.ErrorLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统错误日志
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-14 18:08:46
 */
@Mapper
public interface ErrorLogDao {

	ErrorLogDO get(String errorlogId);
	
	List<ErrorLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ErrorLogDO errorLog);
	
	int update(ErrorLogDO errorLog);
	
	int del(String ERRORLOG_ID);
	
	int batchDel(String[] errorlogIds);
}
