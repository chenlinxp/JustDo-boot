package com.justdo.system.organ.dao;

import com.justdo.common.domain.TreeNode;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.domain.OrganDeptVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 机构
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-07-06 15:08:01
 */
@Mapper
public interface OrganDao {

	/**
	 * 获取机构实体
	 * @param organid
	 * @return
	 */
	OrganDO get(String organid);

	/**
	 * 获取机构实体列表
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
	 * 编辑机构
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
	 * 获取上级机构列表
	 * @return
	 */
	String[] listParentOrgan(String organid);

	/**
	 * 获取机构部门的实体list
	 * @param param
	 * @return
	 */
	List<OrganDeptVO> findOrganDept(Map<String, Object> param);

	/**
	 * 获取机构树节点list
	 * @param param
	 * @return
	 */
	List<TreeNode> getOrgans(Map<String, Object> param);
}
