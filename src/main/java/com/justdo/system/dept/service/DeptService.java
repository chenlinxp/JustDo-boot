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
	/**
	 * 部门实体
	 * @param deptId
	 * @return
	 */
	DeptDO get(String deptId);

	/**
	 * 获取部门实体list
	 * @param map
	 * @return
	 */
	List<DeptDO> list(Map<String, Object> map);

	/**
	 * 获取部门数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存部门
	 * @param dept
	 * @return
	 */
	int save(DeptDO dept);

	/**
	 * 编辑部门
	 * @param dept
	 * @return
	 */
	int update(DeptDO dept);

	/**
	 * 删除实体
	 * @param deptId
	 * @return
	 */
	int del(String deptId);

	/**
	 * 批量删除
	 * @param deptIds
	 * @return
	 */
	int batchDel(String[] deptIds);

	/**
	 * 获取部门树
	 * @param map
	 * @return
	 */
	Tree<DeptDO> getTree(Map<String,Object> map);

	/**
	 * 检查部门下是否有员工
	 * @param deptId
	 * @return
	 */
	boolean checkDeptHasEmployee(String deptId);

	/**
	 * 获取所有部门list
	 * @param map
	 * @return
	 */
	List<DeptVO> getAllDeptList(Map<String,Object> map);

	/**
	 * 获取某机构下的部门树节点list
	 * @param param
	 * @param organid
	 * @return
	 */
	List<TreeNode> getDepts(Map<String, Object> param,String organid);

	/**
	 * 获取 某机构下的顶级部门树节点list
	 * @param param
	 * @return
	 */
	List<TreeNode> getTopDepts(Map<String, Object> param);

	/**
	 * 获取部门树节点list
	 * @param param
	 * @return
	 */
	List<TreeNode> getAllDeptTreeList(Map<String, Object> param);
}
