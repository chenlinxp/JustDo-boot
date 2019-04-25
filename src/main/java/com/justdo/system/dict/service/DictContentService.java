package com.justdo.system.dict.service;

import com.justdo.system.dict.domain.DictContentDO;

import java.util.List;
import java.util.Map;

/**
 * 数据字典的内容
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-02 11:29:02
 */
public interface DictContentService {

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
	 * 获取数据字典内容名称
	 * @param Dicttype
	 * @param value
	 * @return
	 */
	String getName(String Dicttype, String value);

	/**
	 * 根据dcode获取数据字典内容list
	 *
	 * @param dcode
	 * @return
	 */
	List<DictContentDO> listDictByCode(String dcode);

	/**
	 * 根据did获取数据字典内容list
	 * @param did
	 * @return
	 */
	List<DictContentDO> listByTypeId(String did);

}
