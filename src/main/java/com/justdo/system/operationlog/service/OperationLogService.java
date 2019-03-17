package com.justdo.system.operationlog.service;

import com.justdo.common.domain.PageDO;
import com.justdo.common.utils.Query;
import com.justdo.system.operationlog.domain.OperationLogDO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public interface OperationLogService {


	OperationLogDO get(String id);

	List<OperationLogDO> list(Map<String,Object> map);

	int count(Map<String,Object> map);

	void save(OperationLogDO operationLogDO);

	PageDO<OperationLogDO> queryList(Query query);

	int del(String id);

	int batchDel(String[] ids);

}
