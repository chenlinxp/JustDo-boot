package com.justdo.system.city.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.justdo.system.city.dao.CityDao;
import com.justdo.system.city.domain.CityDO;
import com.justdo.system.city.service.CityService;



@Service
public class CityServiceImpl implements CityService {
	@Autowired
	private CityDao cityDao;
	
	@Override
	public CityDO get(String cityid){
		return cityDao.get(cityid);
	}
	
	@Override
	public List<CityDO> list(Map<String, Object> map){
		return cityDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return cityDao.count(map);
	}
	
	@Override
	public int save(CityDO city){
		return cityDao.save(city);
	}
	
	@Override
	public int update(CityDO city){
		return cityDao.update(city);
	}
	
	@Override
	public int del(String cityid){
		return cityDao.del(cityid);
	}
	
	@Override
	public int batchDel(String[] cityids){
		return cityDao.batchDel(cityids);
	}
	
}
