package com.justdo.system.position.service.impl;

import com.justdo.common.domain.Tree;
import com.justdo.common.domain.TreeNode;
import com.justdo.common.utils.BuildTree;
import com.justdo.system.position.dao.PositionDao;
import com.justdo.system.position.domain.PositionDO;
import com.justdo.system.position.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
		String deptid = map.get("deptid").toString();
		for (PositionDO position : positions) {
			Tree<PositionDO> tree = new Tree<PositionDO>();
			tree.setId(position.getPostid().toString());
			tree.setParentId(position.getPostpid().toString());

			tree.setText(position.getPostname());
			tree.setIcon("fa fa-users");
			Map<String, Object> state = new HashMap<>(1);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<PositionDO> t = BuildTree.build(trees,deptid);
		return t;
	}

	@Override
	public List<TreeNode> getPositions(Map<String, Object> param,String deptid){

		List<TreeNode> postionTree = new ArrayList<TreeNode>();
		List<TreeNode> subpositionTree = positionDao.getPositions(param);
		if(subpositionTree!=null){
			for(TreeNode treeNode:subpositionTree){
				TreeNode treeNode2 = new TreeNode();
				treeNode2.setId(treeNode.getId());
				treeNode2.setText(treeNode.getText());
				treeNode2.setParentid(treeNode.getParentid());
				treeNode2.setIcon("fa fa-users");
				Map<String, Object> treeMap = new HashMap<>(1);
				treeMap.put("type","post");
				treeMap.put("deptid",deptid);
				treeNode2.setAttributes(treeMap);
				Map<String, Object> state = new HashMap<>(16);
				state.put("opened", false);
				treeNode2.setState(state);
				Map<String ,Object> paramMap = new HashMap<String ,Object>();
				String a = treeNode.getId();
				paramMap.put("postpid" , treeNode.getId());
				paramMap.put("deptid" , deptid);
				treeNode2.setChildren(getPositions(paramMap,deptid));
				postionTree.add(treeNode2);
			}
		}
		return postionTree;
	}

	@Override
	public List<TreeNode> getTopPosions(Map<String, Object> param){
		List<TreeNode> topdeptTree = positionDao.getTopPosions(param);
		return topdeptTree;
	}
}
