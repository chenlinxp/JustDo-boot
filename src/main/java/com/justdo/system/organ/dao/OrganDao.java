package com.justdo.system.organ.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.justdo.system.organ.domain.OrganDO;

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
	
	int remove(String ORGANID);
	
	int batchDel(String[] organids);
}
