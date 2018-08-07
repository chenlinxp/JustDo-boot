package com.justdo.system.province.service;

import com.justdo.system.province.domain.ProvinceDO;

import java.util.List;
import java.util.Map;

/**
 * 省份编码表
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:20:40
 */
public interface ProvinceService {
	
	ProvinceDO get(String provinceid);
	
	List<ProvinceDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ProvinceDO province);
	
	int update(ProvinceDO province);
	
	int del(String provinceid);
	
	int batchDel(String[] provinceids);
}
