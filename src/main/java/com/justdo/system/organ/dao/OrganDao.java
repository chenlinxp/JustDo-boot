package com.justdo.system.organ.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.justdo.common.domain.TreeNode;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.domain.OrganDeptVO;

/**
 * 机构
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-07-06 15:08:01
 */
@Mapper
public interface OrganDao {

	OrganDO get(String organid);
	
	List<OrganDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrganDO organ);
	
	int update(OrganDO organ);
	
	int del(String organid);
	
	int batchDel(String[] organids);

	String[] listParentOrgan();

	List<OrganDeptVO> findOrganDept(Map<String, Object> param);

	List<TreeNode> getOrgans(Map<String, Object> param);
}
