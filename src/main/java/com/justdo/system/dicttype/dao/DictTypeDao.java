package com.justdo.system.dicttype.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.justdo.system.dicttype.domain.DictTypeDO;




/**
 * 字典表
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface DictTypeDao {

	DictTypeDO get(String did);

	int save(DictTypeDO dict);

	int update(DictTypeDO dict);

	int remove(String did);

	List<DictTypeDO> list(Map<String, Object> map);

}
