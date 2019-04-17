package com.justdo.system.resource.service.impl;

import com.justdo.common.domain.Tree;
import com.justdo.common.utils.BuildTree;
import com.justdo.system.resource.dao.ResourceDao;
import com.justdo.system.resource.domain.ResourceDO;
import com.justdo.system.resource.service.ResourceService;
import com.justdo.system.role.dao.RoleResourceDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private ResourceDao resourceDao;

	@Autowired
	private RoleResourceDao roleResourceDao;

	
	@Override
	public ResourceDO get(String resourceId){
		return resourceDao.get(resourceId);
	}
	
	@Override
	public List<ResourceDO> list(Map<String, Object> map){
		return resourceDao.list(map);
	}

	@Override
	public Tree<ResourceDO> getEmployeeResourceTree(String employeeId){

		List<Tree<ResourceDO>> trees = new ArrayList<Tree<ResourceDO>>();
		List<ResourceDO> resourceDOs = resourceDao.listResourceByEmployeeId(employeeId);
		for (ResourceDO resourceDO : resourceDOs) {
			Tree<ResourceDO> tree = new Tree<ResourceDO>();
			tree.setId(resourceDO.getResourceId());
			tree.setParentId(resourceDO.getParentId().toString());
			tree.setText(resourceDO.getResourceName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("url", resourceDO.getResourceUrl());
			attributes.put("icon", resourceDO.getResourceIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<ResourceDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public Tree<ResourceDO> getTree(){

		List<Tree<ResourceDO>> trees = new ArrayList<Tree<ResourceDO>>();
		List<ResourceDO> resourceDOs = resourceDao.list(new HashMap<>(16));
		for (ResourceDO resourceDO : resourceDOs) {
			Tree<ResourceDO> tree = new Tree<ResourceDO>();
			tree.setId(resourceDO.getResourceId());
			tree.setParentId(resourceDO.getParentId().toString());
			tree.setText(resourceDO.getResourceName());
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<ResourceDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public Tree<ResourceDO> getTree(String roleId){
		// 根据roleId查询权限
		List<ResourceDO> resourceDOs = resourceDao.list(new HashMap<String, Object>(16));
		List<String> resourceIds = roleResourceDao.listResourceIdByRoleId(roleId);
		List<String> temp = resourceIds;
		for (ResourceDO resource : resourceDOs) {
			if (temp.contains(resource.getParentId())) {
				resourceIds.remove(resource.getParentId());
			}
		}
		List<Tree<ResourceDO>> trees = new ArrayList<Tree<ResourceDO>>();
		List<ResourceDO> resourceDOs2 = resourceDao.list(new HashMap<String, Object>(16));
		for (ResourceDO resourceDO : resourceDOs2) {
			Tree<ResourceDO> tree = new Tree<ResourceDO>();
			tree.setId(resourceDO.getResourceId());
			tree.setParentId(resourceDO.getParentId().toString());
			tree.setText(resourceDO.getResourceName());
			Map<String, Object> state = new HashMap<>(16);
			String resourceId = resourceDO.getResourceId();
			if (resourceIds.contains(resourceId)) {
				state.put("selected", true);
			} else {
				state.put("selected", false);
			}
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<ResourceDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public Set<String> listEmployeePermissions(String employeeId){
		List<String> perms = resourceDao.listEmployeePermissions(employeeId);
		Set<String> permsSet = new HashSet<>();
		for (String perm : perms) {
			if (StringUtils.isNotBlank(perm)) {
				permsSet.addAll(Arrays.asList(perm.trim().split(",")));
			}
		}
		return permsSet;
	}

	@Override
	public List<Tree<ResourceDO>> listEmployeeResourceTree(String employeeId) {
		List<Tree<ResourceDO>> trees = new ArrayList<Tree<ResourceDO>>();
		List<ResourceDO> menuDOs = resourceDao.listResourceByEmployeeId(employeeId);
		for (ResourceDO resourceDO : menuDOs) {
			Tree<ResourceDO> tree = new Tree<ResourceDO>();
			tree.setId(resourceDO.getResourceId().toString());
			tree.setParentId(resourceDO.getParentId().toString());
			tree.setText(resourceDO.getResourceName());
			Map<String, Object> attributes = new HashMap<>(16);
			attributes.put("url", resourceDO.getResourceUrl());
			attributes.put("icon", resourceDO.getResourceIcon());
			tree.setAttributes(attributes);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<Tree<ResourceDO>> list = BuildTree.buildList(trees, "0");
		return list;
	}

	@Override
	public int count(Map<String, Object> map){
		return resourceDao.count(map);
	}


	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(ResourceDO resource){
		return resourceDao.save(resource);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(ResourceDO resource){
		return resourceDao.update(resource);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String resourceId){
		return resourceDao.del(resourceId);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] resourceIds){
		return resourceDao.batchDel(resourceIds);
	}


	
}
