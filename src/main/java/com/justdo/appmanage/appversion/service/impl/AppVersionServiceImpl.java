package com.justdo.appmanage.appversion.service.impl;

import com.justdo.appmanage.appversion.dao.AppVersionDao;
import com.justdo.appmanage.appversion.domain.AppVersionDO;
import com.justdo.appmanage.appversion.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;



@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class AppVersionServiceImpl implements AppVersionService {
	@Autowired
	private AppVersionDao appVersionDao;
	
	@Override
	public AppVersionDO get(String appVersionId){
		return appVersionDao.get(appVersionId);
	}
	
	@Override
	public List<AppVersionDO> list(Map<String, Object> map){
		return appVersionDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return appVersionDao.count(map);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(AppVersionDO appVersion){
		return appVersionDao.save(appVersion);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(AppVersionDO appVersion){
		return appVersionDao.update(appVersion);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String appVersionId){
		return appVersionDao.del(appVersionId);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] appVersionIds){
		return appVersionDao.batchDel(appVersionIds);
	}


	
}
