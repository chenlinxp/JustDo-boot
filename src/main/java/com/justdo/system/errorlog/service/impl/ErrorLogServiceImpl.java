package com.justdo.system.errorlog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.justdo.system.errorlog.dao.ErrorLogDao;
import com.justdo.system.errorlog.domain.ErrorLogDO;
import com.justdo.system.errorlog.service.ErrorLogService;



@Service
public class ErrorLogServiceImpl implements ErrorLogService {
	@Autowired
	private ErrorLogDao errorLogDao;
	
	@Override
	public ErrorLogDO get(Long errorlogId){
		return errorLogDao.get(errorlogId);
	}
	
	@Override
	public List<ErrorLogDO> list(Map<String, Object> map){
		return errorLogDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return errorLogDao.count(map);
	}
	
	@Override
	public int save(ErrorLogDO errorLog){
		return errorLogDao.save(errorLog);
	}
	
	@Override
	public int update(ErrorLogDO errorLog){
		return errorLogDao.update(errorLog);
	}
	
	@Override
	public int del(Long errorlogId){
		return errorLogDao.del(errorlogId);
	}
	
	@Override
	public int batchDel(Long[] errorlogIds){
		return errorLogDao.batchDel(errorlogIds);
	}
	
}
