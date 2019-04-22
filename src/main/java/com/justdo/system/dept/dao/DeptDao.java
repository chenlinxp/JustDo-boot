package com.justdo.system.dept.dao;

import com.justdo.common.domain.TreeNode;
import com.justdo.system.dept.domain.DeptDO;
import com.justdo.system.dept.domain.DeptVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface DeptDao {

	DeptDO get(String deptId);
	
	List<DeptDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(DeptDO dept);
	
	int update(DeptDO dept);
	
	int del(String deptId);
	
	int batchDel(String[] deptIds);
	
	String[] listParentDept();

	List<DeptVO> getAllDepts(Map<String, Object> param);

	List<TreeNode> getDepts(Map<String, Object> param);


	List<TreeNode> getTopDepts(Map<String, Object> param);



}
