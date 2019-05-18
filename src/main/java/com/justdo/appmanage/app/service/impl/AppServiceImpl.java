package com.justdo.appmanage.app.service.impl;

import com.justdo.appmanage.app.dao.AppDao;
import com.justdo.appmanage.app.domain.AppDO;
import com.justdo.appmanage.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class AppServiceImpl implements AppService {
	@Autowired
	private AppDao appDao;
	
	@Override
	public AppDO get(String appId){
		return appDao.get(appId);
	}
	
	@Override
	public List<AppDO> list(Map<String, Object> map){
		return appDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return appDao.count(map);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(AppDO app){
		return appDao.save(app);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(AppDO app){
		return appDao.update(app);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String appId){
		return appDao.del(appId);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] appIds){
		return appDao.batchDel(appIds);
	}
	
}
