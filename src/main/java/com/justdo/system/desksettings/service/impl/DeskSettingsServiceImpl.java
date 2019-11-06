package com.justdo.system.desksettings.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import com.justdo.system.desksettings.dao.DeskSettingsDao;
import com.justdo.system.desksettings.domain.DeskSettingsDO;
import com.justdo.system.desksettings.service.DeskSettingsService;



@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class DeskSettingsServiceImpl implements DeskSettingsService {
	@Autowired
	private DeskSettingsDao deskSettingsDao;
	
	@Override
	public DeskSettingsDO get(String settingsId){
		return deskSettingsDao.get(settingsId);
	}

	@Override
	public DeskSettingsDO getByEmployeeId(String EmployeeId){
		return deskSettingsDao.getByEmployeeId(EmployeeId);
	}
	
	@Override
	public List<DeskSettingsDO> list(Map<String, Object> map){
		return deskSettingsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return deskSettingsDao.count(map);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(DeskSettingsDO deskSettings){
		return deskSettingsDao.save(deskSettings);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(DeskSettingsDO deskSettings){
		return deskSettingsDao.update(deskSettings);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String settingsId){
		return deskSettingsDao.del(settingsId);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] settingsIds){
		return deskSettingsDao.batchDel(settingsIds);
	}
	
}
