package com.justdo.system.dept.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.justdo.common.domain.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justdo.common.domain.Tree;
import com.justdo.common.utils.BuildTree;
import com.justdo.system.organ.dao.OrganDao;
import com.justdo.system.dept.domain.DeptVO;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.dept.dao.DeptDao;
import com.justdo.system.dept.domain.DeptDO;
import com.justdo.system.dept.service.DeptService;


/**
 * 部门管理
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptDao deptDao;

//	@Autowired
//	private OrganDao organDao;
	@Override
	public DeptDO get(String deptId){
		return deptDao.get(deptId);
	}

	@Override
	public List<DeptDO> list(Map<String, Object> map){
		return deptDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return deptDao.count(map);
	}

	@Override
	public int save(DeptDO dept){
		return deptDao.save(dept);
	}

	@Override
	public int update(DeptDO dept){
		return deptDao.update(dept);
	}

	@Override
	public int del(String deptId){
		return deptDao.del(deptId);
	}

	@Override
	public int batchDel(String[] deptIds){
		return deptDao.batchDel(deptIds);
	}

	@Override
	public Tree<DeptDO> getTree() {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> depts = deptDao.list(new HashMap<String,Object>(16));
//		List<OrganDO> organs = organDao.list(new HashMap<String,Object>(16));
//
//		for (OrganDO organ : organs) {
//			Tree<DeptDO> tree = new Tree<DeptDO>();
//			tree.setId(organ.getOrganid());
//			tree.setParentId(organ.getOrganpid());
//			tree.setText(organ.getOrganname());
//			Map<String, Object> state = new HashMap<>(16);
//			state.put("opened", true);
//			tree.setState(state);
//			tree.setNodeType("ORGAN");
//			trees.add(tree);
//		}

		for (DeptDO dept : depts) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(dept.getDeptid().toString());
			tree.setParentId(dept.getDeptpid().toString());
			tree.setText(dept.getDeptname());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public boolean checkDeptHasUser(String deptId) {
		// TODO Auto-generated method stub
		//查询部门以及此部门的下级部门
		int result = deptDao.getDeptUserNumber(deptId);
		return result==0?true:false;
	}
	@Override
	public List<DeptVO> getAllDepts(Map<String, Object> param){
		List<DeptVO> deptvoList = new ArrayList<DeptVO>();
		List<DeptVO> deptVos = deptDao.getAllDepts(param);
		if(deptVos!=null){
			for(DeptVO deptVO:deptVos){
				DeptVO depteVo2 = new DeptVO();
				depteVo2.setDeptid(deptVO.getDeptid());
				depteVo2.setDeptname(deptVO.getDeptname());
				Map<String ,Object> paramMap = new HashMap<String ,Object>();

				paramMap.put("deptpid" , deptVO.getDeptid());
				paramMap.put("organid" ,deptVO.getOrganid());

    				depteVo2.setSubdeptvo(getAllDepts(paramMap));
				deptvoList.add(depteVo2);
			}
		}
		return  deptvoList;
	}
	@Override
	public List<TreeNode> getTopDepts(Map<String, Object> param){
		List<TreeNode> topdeptTree = deptDao.getTopDepts(param);
		return topdeptTree;
	}

	@Override
	public List<TreeNode> getDepts(Map<String, Object> param){
		List<TreeNode> deptTree = new ArrayList<TreeNode>();
		List<TreeNode> subdeptTree = deptDao.getDepts(param);
		if(subdeptTree!=null){
			for(TreeNode treeNode:subdeptTree){
				TreeNode treeNode2 = new TreeNode();
				treeNode2.setId(treeNode.getId());
				treeNode2.setText(treeNode.getText());
				treeNode2.setParentid(treeNode.getParentid());
				treeNode2.setIcon("fa fa-home");
				Map<String, Object> state = new HashMap<>(16);
				state.put("opened", true);
				treeNode2.setState(state);

				Map<String ,Object> paramMap = new HashMap<String ,Object>();
				paramMap.put("deptpid" , treeNode.getId());
				paramMap.put("organid" ,treeNode.getParentid());

				treeNode2.setChildren(getDepts(paramMap));
				deptTree.add(treeNode2);
			}
		}
		return deptTree;
	}

	@Override
	public List<TreeNode> getAllDepts2(Map<String, Object> param){
		List<TreeNode> deptTreeList = new ArrayList<TreeNode>();
		List<TreeNode> topdeptTree =getTopDepts(param);
		if(topdeptTree!=null){
			for(TreeNode treeNode:topdeptTree){
				TreeNode treeNode2 = new TreeNode();
				treeNode2.setId(treeNode.getId());
				treeNode2.setText(treeNode.getText());
				treeNode2.setParentid(treeNode.getParentid());
				treeNode2.setIcon("fa fa-home");
				Map<String, Object> state = new HashMap<>(16);
				state.put("opened", true);
				treeNode2.setState(state);

				Map<String ,Object> paramMap = new HashMap<String ,Object>();
				paramMap.put("deptpid" , treeNode.getId());
				paramMap.put("organid" ,treeNode.getParentid());

				treeNode2.setChildren(getDepts(paramMap));
				deptTreeList.add(treeNode2);
			}
		}
		return  deptTreeList;
	}

}
