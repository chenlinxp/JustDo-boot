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

	DictContentDO get(String dcid);

	List<DictContentDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(DictContentDO dictContent);

	int update(DictContentDO dictContent);

	int del(String DCID);

	int batchDel(String[] dcids);

	List<DictContentDO> listDictByCode(String dcode);
}

