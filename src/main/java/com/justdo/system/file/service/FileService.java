package com.justdo.system.file.service;

import java.util.List;
import java.util.Map;

import com.justdo.system.file.domain.FileDO;


/**
 * 文件上传
 * 
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public interface FileService {
	
	FileDO get(String id);
	
	List<FileDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(FileDO fileDao);
	
	int update(FileDO fileDao);
	
	int del(String id);
	
	int batchDel(String[] ids);

	/**
	 * 判断一个文件是否存在
	 * @param url FileDO中存的路径
	 * @return
	 */
    Boolean isExist(String url);
}
