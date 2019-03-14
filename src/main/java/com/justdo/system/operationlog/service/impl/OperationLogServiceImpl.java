package com.justdo.system.operationlog.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.justdo.system.operationlog.domain.OperationLogDO;
import com.justdo.common.domain.PageDO;
import com.justdo.system.operationlog.service.OperationLogService;
import com.justdo.common.utils.Query;

/**
 * 系统日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
	@Autowired
	com.justdo.system.operationlog.dao.OperationLogDao operationlogdao;

	@Async
	@Override
	public void save(OperationLogDO logDO) {
		operationlogdao.save(logDO);
	}

	@Override
	public PageDO<OperationLogDO> queryList(Query query) {
		int total = operationlogdao.count(query);
		List<OperationLogDO> logs = operationlogdao.list(query);
		PageDO<OperationLogDO> page = new PageDO<>();
		page.setTotal(total);
		page.setRows(logs);
		return page;
	}

	@Override
	public int del(String id) {
		int count = operationlogdao.del(id);
		return count;
	}

	@Override
	public int batchDel(String[] ids){
		return operationlogdao.batchDel(ids);
	}
}
