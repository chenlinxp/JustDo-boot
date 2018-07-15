package com.justdo.system.file.dao;

import java.util.List;
import java.util.Map;

import com.justdo.system.file.domain.FileDO;


import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface FileDao {

	FileDO get(String id);
	
	List<FileDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(FileDO file);
	
	int update(FileDO file);
	
	int del(String id);
	
	int batchDel(String[] ids);
}
