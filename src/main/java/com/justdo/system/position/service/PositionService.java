package com.justdo.system.position.service;

import com.justdo.common.domain.Tree;
import com.justdo.common.domain.TreeNode;
import com.justdo.system.position.domain.PositionDO;

import java.util.List;
import java.util.Map;

/**
 * 岗位管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-16 23:21:36
 */
public interface PositionService {

	/**
	 * 返回实体
	 * @param postid
	 * @return PositionDO
	 */
		PositionDO get(String postid);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<PositionDO> list(Map<String, Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存实体
	 * @param position
	 * @return
	 */
	int save(PositionDO position);

	/**
	 * 更新实体
	 * @param position
	 * @return list
	 */
	int update(PositionDO position);

	/**
	 * 删除实体
	 * @param postid
	 * @return list
	 */
	int del(String postid);

	/**
	 * 批量删除实体
	 * @param postids
	 * @return list
	 */
	int batchDel(String[] postids);

	/**
	 * 获取岗位树
	 * @param map
	 * @return
	 */
	Tree<PositionDO> getTree(Map<String,Object> map);

	/**
	 * 获取某部门下的岗位树节点list
	 * @param param
	 * @return
	 */
	List<TreeNode> getPositions(Map<String, Object> param,String deptid);

	/**
	 * 获取 某部门下的顶级岗位树节点list
	 * @param param
	 * @return
	 */
	List<TreeNode> getTopPosions(Map<String, Object> param);
}
