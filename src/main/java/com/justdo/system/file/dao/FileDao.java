package com.justdo.system.file.dao;

import com.justdo.system.file.domain.FileDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface FileDao {

	FileDO get(String fileId);
	
	List<FileDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(FileDO file);
	
	int update(FileDO file);
	
	int del(String fileId);
	
	int batchDel(String[] fileIds);
}
