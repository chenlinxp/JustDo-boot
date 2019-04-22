package com.justdo.system.organ.service;


import com.justdo.common.domain.Tree;
import com.justdo.common.domain.TreeNode;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.domain.OrganDeptVO;

import java.util.List;
import java.util.Map;

/**
 * 机构
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-07-06 15:08:01
 */
public interface OrganService {

	/**
	 * 获取机构实体
	 * @param organid
	 * @return
	 */
	OrganDO get(String organid);

	/**
	 * 获取机构列表
	 * @param map
	 * @return
	 */
	List<OrganDO> list(Map<String, Object> map);

	/**
	 * 获取机构数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存机构
	 * @param organ
	 * @return
	 */
	int save(OrganDO organ);

	/**
	 * 更新机构
	 * @param organ
	 * @return
	 */
	int update(OrganDO organ);

	/**
	 * 删除机构
	 * @param organid
	 * @return
	 */
	int del(String organid);

	/**
	 * 批量删除机构
	 * @param organids
	 * @return
	 */
	int batchDel(String[] organids);

	/**
	 * 获取机构树
	 * @return
	 */
	Tree<OrganDO> getTree();

	/**
	 * 机构部门list
	 * @param param
	 * @return
	 */
	List<OrganDeptVO> getOrganDept(Map<String, Object> param);

	/**
	 * 机构部门树节点list
	 * @param param
	 * @return
	 */
	List<TreeNode> getOrganDeptTree(Map<String, Object> param);
}
