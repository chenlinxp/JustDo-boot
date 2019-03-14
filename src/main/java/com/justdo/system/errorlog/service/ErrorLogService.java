package com.justdo.system.errorlog.service;

import com.justdo.system.errorlog.domain.ErrorLogDO;

import java.util.List;
import java.util.Map;

/**
 * 系统错误日志
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-14 18:08:46
 */
public interface ErrorLogService {
	
	ErrorLogDO get(Long errorlogId);
	
	List<ErrorLogDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ErrorLogDO errorLog);
	
	int update(ErrorLogDO errorLog);
	
	int del(Long errorlogId);
	
	int batchDel(Long[] errorlogIds);
}
