package com.justdo.system.province.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.justdo.system.province.dao.ProvinceDao;
import com.justdo.system.province.domain.ProvinceDO;
import com.justdo.system.province.service.ProvinceService;



@Service
public class ProvinceServiceImpl implements ProvinceService {
	@Autowired
	private ProvinceDao provinceDao;
	
	@Override
	public ProvinceDO get(String provinceid){
		return provinceDao.get(provinceid);
	}
	
	@Override
	public List<ProvinceDO> list(Map<String, Object> map){
		return provinceDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return provinceDao.count(map);
	}
	
	@Override
	public int save(ProvinceDO province){
		return provinceDao.save(province);
	}
	
	@Override
	public int update(ProvinceDO province){
		return provinceDao.update(province);
	}
	
	@Override
	public int del(String provinceid){
		return provinceDao.del(provinceid);
	}
	
	@Override
	public int batchDel(String[] provinceids){
		return provinceDao.batchDel(provinceids);
	}
	
}
