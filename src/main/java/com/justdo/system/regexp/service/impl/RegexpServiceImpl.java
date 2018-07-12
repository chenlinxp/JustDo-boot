package com.justdo.system.regexp.service.impl;

import com.justdo.system.regexp.domain.RegexpEXDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.justdo.system.regexp.dao.RegexpDao;
import com.justdo.system.regexp.domain.RegexpDO;
import com.justdo.system.regexp.service.RegexpService;



@Service
public class RegexpServiceImpl implements RegexpService {
	@Autowired
	private RegexpDao regexpDao;
	
	@Override
	public RegexpDO get(String rid){
		return regexpDao.get(rid);
	}
	
	@Override
	public List<RegexpDO> list(Map<String, Object> map){
		return regexpDao.list(map);
	}


	@Override
	public List<RegexpEXDO> listEX(Map<String,Object> map){
		return regexpDao.listEX(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return regexpDao.count(map);
	}
	
	@Override
	public int save(RegexpDO regexp){
		return regexpDao.save(regexp);
	}
	
	@Override
	public int update(RegexpDO regexp){
		return regexpDao.update(regexp);
	}
	
	@Override
	public int del(String rid){
		return regexpDao.del(rid);
	}
	
	@Override
	public int batchDel(String[] rids){
		return regexpDao.batchDel(rids);
	}
	
}
