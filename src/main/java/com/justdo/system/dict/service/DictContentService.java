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

	DictContentDO get(String dcid);

	List<DictContentDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(DictContentDO dictContent);

	int update(DictContentDO dictContent);

	int del(String dcid);

	int batchDel(String[] dcids);

	String getName(String type, String value);

	List<DictContentDO> getSexList();

	List<DictContentDO> listByType(String id);
}
