package com.justdo.system.dept.service;

import java.util.List;
import java.util.Map;

import com.justdo.common.domain.Tree;
import com.justdo.system.dept.domain.DeptDO;



/**
 * 部门管理
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public interface DeptService {
	
	DeptDO get(Long deptId);
	
	List<DeptDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(DeptDO dept);
	
	int update(DeptDO dept);
	
	int remove(Long deptId);
	
	int batchDel(Long[] deptIds);

	Tree<DeptDO> getTree();
	
	boolean checkDeptHasUser(Long deptId);
}
