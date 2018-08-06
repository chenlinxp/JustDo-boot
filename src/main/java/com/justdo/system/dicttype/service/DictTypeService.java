package com.justdo.system.dicttype.service;

import java.util.List;
import java.util.Map;

import com.justdo.system.dicttype.domain.DictTypeDO;




/**
 * 字典索引表
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-21 18:28:07
 */
public interface DictTypeService {

	DictTypeDO get(String id);

	int save(DictTypeDO dict);

	int update(DictTypeDO dict);

	int del(String id);

	List<DictTypeDO>  list(Map<String, Object> params);




}
