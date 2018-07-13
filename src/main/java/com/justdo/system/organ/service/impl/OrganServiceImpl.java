package com.justdo.system.organ.service.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.justdo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justdo.system.organ.dao.OrganDao;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.service.OrganService;
import com.justdo.common.domain.Tree;
import com.justdo.common.utils.BuildTree;



@Service
public class OrganServiceImpl implements OrganService {
	@Autowired
	private OrganDao organDao;
	
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
	public int save(OrganDO organ){
		return organDao.save(organ);
	}
	
	@Override
	public int update(OrganDO organ){
		return organDao.update(organ);
	}
	
	@Override
	public int del(String organid){
		return organDao.del(organid);
	}
	
	@Override
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
	
}
