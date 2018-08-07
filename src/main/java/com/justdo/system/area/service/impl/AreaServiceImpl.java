package com.justdo.system.area.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.justdo.system.area.dao.AreaDao;
import com.justdo.system.area.domain.AreaDO;
import com.justdo.system.area.service.AreaService;



@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;
	
	@Override
	public AreaDO get(String areaid){
		return areaDao.get(areaid);
	}
	
	@Override
	public List<AreaDO> list(Map<String, Object> map){
		return areaDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return areaDao.count(map);
	}
	
	@Override
	public int save(AreaDO area){
		return areaDao.save(area);
	}
	
	@Override
	public int update(AreaDO area){
		return areaDao.update(area);
	}
	
	@Override
	public int del(String areaid){
		return areaDao.del(areaid);
	}
	
	@Override
	public int batchDel(String[] areaids){
		return areaDao.batchDel(areaids);
	}
	
}
