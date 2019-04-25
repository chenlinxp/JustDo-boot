package com.justdo.system.dict.dao;

import com.justdo.system.dict.domain.DictContentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;



/**
 * 字典表
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface DictContentDao {

	/**
	 * 获取数据字典实体
	 * @param dcid
	 * @return
	 */
	DictContentDO get(String dcid);

	/**
	 * 获取数据字典实体list
	 * @param map
	 * @return
	 */
	List<DictContentDO> list(Map<String, Object> map);

	/**
	 * 获取数据字典实体数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存获取数据字典实体
	 * @param dictContent
	 * @return
	 */
	int save(DictContentDO dictContent);

	/**
	 * 编辑获取数据字典实体
	 * @param dictContent
	 * @return
	 */
	int update(DictContentDO dictContent);

	/**
	 * 删除数据字典实体
	 * @param dcid
	 * @return
	 */
	int del(String dcid);

	/**
	 * 批量删除数据字典实体
	 * @param dcids
	 * @return
	 */
	int batchDel(String[] dcids);

	/**
	 * 根据dcode获取数据字典内容list
	 *
	 * @param dcode
	 * @return
	 */
	List<DictContentDO> listDictByCode(String dcode);
}

