package com.justdo.system.province.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.justdo.system.province.domain.ProvinceDO;

/**
 * 省份编码表
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:20:40
 */
@Mapper
public interface ProvinceDao {

	ProvinceDO get(String provinceid);
	
	List<ProvinceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProvinceDO province);
	
	int update(ProvinceDO province);
	
	int del(String PROVINCEID);
	
	int batchDel(String[] provinceids);
}
