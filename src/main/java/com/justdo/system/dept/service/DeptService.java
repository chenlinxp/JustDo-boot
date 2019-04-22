package com.justdo.system.dept.service;

import com.justdo.common.domain.Tree;
import com.justdo.common.domain.TreeNode;
import com.justdo.system.dept.domain.DeptDO;
import com.justdo.system.dept.domain.DeptVO;

import java.util.List;
import java.util.Map;


/**
 * 部门管理
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public interface DeptService {
	
	DeptDO get(String deptId);
	
	List<DeptDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DeptDO dept);
	
	int update(DeptDO dept);
	
	int del(String deptId);
	
	int batchDel(String[] deptIds);

	Tree<DeptDO> getTree(Map<String,Object> map);
	
	boolean checkDeptHasUser(String deptId);

	List<DeptVO> getAllDeptList(Map<String,Object> map);

	List<TreeNode> getDepts(Map<String, Object> param,String organid);

	List<TreeNode> getTopDepts(Map<String, Object> param);

	List<TreeNode> getAllDeptTreeList(Map<String, Object> param);
}
