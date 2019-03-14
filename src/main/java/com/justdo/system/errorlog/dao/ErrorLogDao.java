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

	ErrorLogDO get(Long errorlogId);
	
	List<ErrorLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ErrorLogDO errorLog);
	
	int update(ErrorLogDO errorLog);
	
	int del(Long ERRORLOG_ID);
	
	int batchDel(Long[] errorlogIds);
}
