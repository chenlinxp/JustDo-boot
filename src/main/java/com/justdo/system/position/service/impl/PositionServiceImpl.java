package com.justdo.system.position.service.impl;

import com.justdo.common.domain.Tree;
import com.justdo.common.utils.BuildTree;
import com.justdo.system.dept.domain.DeptDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.justdo.system.position.dao.PositionDao;
import com.justdo.system.position.domain.PositionDO;
import com.justdo.system.position.service.PositionService;



@SuppressWarnings("AlibabaRemoveCommentedCode")
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class PositionServiceImpl implements PositionService {
	@Autowired
	private PositionDao positionDao;
	
	@Override
	public PositionDO get(String postid){
		return positionDao.get(postid);
	}
	
	@Override
	public List<PositionDO> list(Map<String, Object> map){
		return positionDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return positionDao.count(map);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(PositionDO position){
		return positionDao.save(position);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(PositionDO position){
		return positionDao.update(position);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String postid){
		return positionDao.del(postid);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] postids){
		return positionDao.batchDel(postids);
	}

	@Override
	public Tree<PositionDO> getTree(Map<String,Object> map) {
		List<Tree<PositionDO>> trees = new ArrayList<Tree<PositionDO>>();
		List<PositionDO> positions = positionDao.list(map);
		for (PositionDO position : positions) {
			Tree<PositionDO> tree = new Tree<PositionDO>();
			tree.setId(position.getPostid().toString());
			tree.setParentId(position.getPostpid().toString());
			tree.setText(position.getPostname());
			Map<String, Object> state = new HashMap<>(1);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<PositionDO> t = BuildTree.build(trees);
		return t;
	}
	
}
