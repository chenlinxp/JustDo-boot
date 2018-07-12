package com.justdo.system.organ.service;

import com.justdo.system.organ.domain.OrganDO;

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
	
	OrganDO get(String organid);
	
	List<OrganDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(OrganDO organ);
	
	int update(OrganDO organ);
	
	int remove(String organid);
	
	int batchDel(String[] organids);
}
