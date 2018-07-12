package com.justdo.system.organ.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.justdo.system.organ.dao.OrganDao;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.service.OrganService;



@Service
public class OrganServiceImpl implements OrganService {
	@Autowired
	private OrganDao organDao;
	
	@Override
	public OrganDO get(String organid){
		return organDao.get(organid);
	}
	
	@Override
	public List<OrganDO> list(Map<String, Object> map){
		return organDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return organDao.count(map);
	}
	
	@Override
	public int save(OrganDO organ){
		return organDao.save(organ);
	}
	
	@Override
	public int update(OrganDO organ){
		return organDao.update(organ);
	}
	
	@Override
	public int remove(String organid){
		return organDao.remove(organid);
	}
	
	@Override
	public int batchDel(String[] organids){
		return organDao.batchDel(organids);
	}
	
}
