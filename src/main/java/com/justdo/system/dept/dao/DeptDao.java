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

	/**
	 * 获取部门实体
	 * @param deptId
	 * @return
	 */
	DeptDO get(String deptId);

	/**
	 * 获取部门实体list
	 * @param map
	 * @return
	 */
	List<DeptDO> list(Map<String,Object> map);

	/**
	 * 获取部门实体数量
	 * @param map
	 * @return
	 */
	int count(Map<String,Object> map);

	/**
	 * 保存部门实体
	 * @param dept
	 * @return
	 */
	int save(DeptDO dept);

	/**
	 * 编辑部门实体
	 * @param dept
	 * @return
	 */
	int update(DeptDO dept);

	/**
	 * 删除部门实体
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
	 * 部门的上级部门IDs
	 * @return
	 */
	String[] listParentDept();

	/**
	 * 获取所有部门list
	 * @param param
	 * @return
	 */
	List<DeptVO> getAllDepts(Map<String, Object> param);

	/**
	 * 获取某机构下的部门树节点list
	 * @param param
	 * @return
	 */
	List<TreeNode> getDepts(Map<String, Object> param);

	/**
	 * 获取 某机构下的顶级部门树节点list
	 * @param param
	 * @return
	 */
	List<TreeNode> getTopDepts(Map<String, Object> param);



}
