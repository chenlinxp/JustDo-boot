package com.justdo.system.organ.service.impl;


import com.justdo.common.domain.Tree;
import com.justdo.common.domain.TreeNode;
import com.justdo.common.utils.BuildTree;
import com.justdo.common.utils.StringUtils;
import com.justdo.system.dept.domain.DeptVO;
import com.justdo.system.dept.service.DeptService;
import com.justdo.system.organ.dao.OrganDao;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.domain.OrganDeptVO;
import com.justdo.system.organ.service.OrganService;
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
public class OrganServiceImpl implements OrganService {
	@Autowired
	private OrganDao organDao;

	@Autowired
	private DeptService deptService;

	@Override
	public OrganDO get(String organid){
		OrganDO  organDO = organDao.get(organid);
		String porganid = organDO.getOrganpid();
		if(StringUtils.isNotEmpty(porganid)){
			OrganDO porganDO = organDao.get(porganid);
			if(porganDO!=null){
				organDO.setOrganpname(porganDO.getOrganname());
				porganDO = null;
			}
		}
		return organDO;
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
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int save(OrganDO organ){
		return organDao.save(organ);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int update(OrganDO organ){
		return organDao.update(organ);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int del(String organid){
		return organDao.del(organid);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = Exception.class)
	public int batchDel(String[] organids){
		return organDao.batchDel(organids);
	}

	@Override
	public Tree<OrganDO> getTree() {
		List<Tree<OrganDO>> trees = new ArrayList<Tree<OrganDO>>();
		List<OrganDO> organs = organDao.list(new HashMap<String,Object>(16));
		for (OrganDO ogans : organs) {
			Tree<OrganDO> tree = new Tree<OrganDO>();
			tree.setId(ogans.getOrganid().toString());
			tree.setParentId(ogans.getOrganpid().toString());
			tree.setText(ogans.getOrganname());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<OrganDO> t = BuildTree.build(trees);
		return t;
	}
	/**
	 * @descript:递归公司，在公司里面递归部门
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<OrganDeptVO> findOrganDept(Map<String, Object> paramMap) {
		List<OrganDeptVO> list=new ArrayList<OrganDeptVO>();
		//查询公司
		List<OrganDeptVO> organDeptVosList=organDao.findOrganDept(paramMap);
		if(organDeptVosList!=null){
			for(OrganDeptVO organDeptVo:organDeptVosList){
				OrganDeptVO organDeptVo2=new OrganDeptVO();
				organDeptVo2.setOrganid(organDeptVo.getOrganid());
				organDeptVo2.setOrganname(organDeptVo.getOrganname());
				organDeptVo2.setOrganpid(organDeptVo.getOrganpid());
				Map<String, Object> dataMap=new HashMap<String, Object>();
				dataMap.put("organpid", organDeptVo.getOrganid());
				//通过父级公司递归公司
				organDeptVo2.setSuborgandeptvo(findOrganDept(dataMap));

				Map<String, Object> dataDeptMap=new HashMap<String, Object>();
				dataDeptMap.put("organid", organDeptVo.getOrganid());
				dataDeptMap.put("deptid", "");
				//通过父级部门递归部门
				List<DeptVO> deptVOList = deptService.getAllDepts(dataDeptMap);
				organDeptVo2.setDeptvo(deptVOList);
				list.add(organDeptVo2);
			}
		}
		return list;
	}

	@Override
	public List<TreeNode> getOrgans(Map<String, Object> param){
		List<TreeNode> list=new ArrayList<TreeNode>();
		//查询公司
		List<TreeNode> organTreeList=organDao.getOrgans(param);
		if(organTreeList!=null){
			for(TreeNode treeNode:organTreeList){
				TreeNode treeNode2 = new TreeNode();
				treeNode2.setId(treeNode.getId());
				treeNode2.setText(treeNode.getText());
				treeNode2.setParentid(treeNode.getParentid());
				treeNode2.setIcon("fa fa-bank");
				Map<String, Object> state = new HashMap<>(16);
				state.put("opened", true);
				treeNode2.setState(state);
				Map<String, Object> dataMap=new HashMap<String, Object>();
				dataMap.put("organpid", treeNode.getId());
				//通过父级公司递归公司
				//treeNode2.setChildren(getOrgans(dataMap));


				List<TreeNode> listchidren = getOrgans(dataMap);

				Map<String, Object> dataDeptMap=new HashMap<String, Object>();
				dataDeptMap.put("organid", treeNode.getId());
				dataDeptMap.put("deptid", "");
				//通过父级部门递归部门
				List<TreeNode> deptTreeList = deptService.getAllDepts2(dataDeptMap);
				listchidren.addAll(deptTreeList);
				treeNode2.setChildren(listchidren);
				list.add(treeNode2);
			}
		}
		return list;
	}
}
