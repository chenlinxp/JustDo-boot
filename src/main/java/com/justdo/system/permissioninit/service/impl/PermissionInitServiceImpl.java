package com.justdo.system.permissioninit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import com.justdo.system.permissioninit.dao.PermissionInitDao;
import com.justdo.system.permissioninit.domain.PermissionInitDO;
import com.justdo.system.permissioninit.service.PermissionInitService;



@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class PermissionInitServiceImpl implements PermissionInitService {
	@Autowired
	private PermissionInitDao permissionInitDao;
	
	@Override
	public PermissionInitDO get(String permissionId){
		return permissionInitDao.get(permissionId);
	}
	
	@Override
	public List<PermissionInitDO> list(Map<String, Object> map){
		return permissionInitDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return permissionInitDao.count(map);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(PermissionInitDO permissionInit){
		return permissionInitDao.save(permissionInit);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(PermissionInitDO permissionInit){
		return permissionInitDao.update(permissionInit);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String permissionId){
		return permissionInitDao.del(permissionId);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] permissionIds){
		return permissionInitDao.batchDel(permissionIds);
	}
	
}
