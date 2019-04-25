package com.justdo.system.dicttype.service;

import com.justdo.system.dicttype.domain.DictTypeDO;

import java.util.List;
import java.util.Map;




/**
 * 字典索引表
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-21 18:28:07
 */
public interface DictTypeService {

	/**
	 * 获取字典索引表实体
	 * @param id
	 * @return
	 */
	DictTypeDO get(String id);

	/**
	 * 保存字典索引表实体
	 * @param dict
	 * @return
	 */
	int save(DictTypeDO dict);

	/**
	 * 更新字典索引表实体
	 * @param dict
	 * @return
	 */
	int update(DictTypeDO dict);

	/**
	 * 删除字典索引表实体
	 * @param id
	 * @return
	 */
	int del(String id);

	/**
	 * 获取字典索引表实体list
	 * @param params
	 * @return
	 */
	List<DictTypeDO>  list(Map<String, Object> params);




}
