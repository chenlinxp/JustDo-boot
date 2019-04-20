package com.justdo.system.operationlog.dao;



import com.justdo.system.operationlog.domain.OperationLogDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
/**
 * 系统日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface OperationLogDao {

	OperationLogDO get(String operationLogId);
	
	List<OperationLogDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(OperationLogDO log);
	
	int update(OperationLogDO log);
	
	int del(String operationLogId);
	
	int batchDel(String[] operationLogIds);
}
