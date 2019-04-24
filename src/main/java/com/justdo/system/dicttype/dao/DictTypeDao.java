package com.justdo.system.dicttype.dao;

import com.justdo.system.dicttype.domain.DictTypeDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;




/**
 * 字典类型表
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface DictTypeDao {

	/**
	 * 获取字典类型实体
	 * @param did
	 * @return
	 */
	DictTypeDO get(String did);

	/**
	 * 保存字典类型
	 * @param dict
	 * @return
	 */
	int save(DictTypeDO dict);

	/**
	 * 编辑字典类型
	 * @param dict
	 * @return
	 */
	int update(DictTypeDO dict);

	/**
	 * 删除字典类型
	 * @param did
	 * @return
	 */
	int del(String did);

	/**
	 * 字典类型list
	 * @param map
	 * @return
	 */
	List<DictTypeDO> list(Map<String, Object> map);

}
