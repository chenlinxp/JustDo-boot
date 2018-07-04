package com.justdo.system.file.service.impl;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.justdo.common.config.JustdoConfig;
import com.justdo.system.file.dao.FileDao;
import com.justdo.system.file.domain.FileDO;
import com.justdo.system.file.service.FileService;


/**
 * 文件上传
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileDao fileDao;

	@Autowired
	private JustdoConfig justdoConfig;
	@Override
	public FileDO get(Long id){
		return fileDao.get(id);
	}
	
	@Override
	public List<FileDO> list(Map<String, Object> map){
		return fileDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return fileDao.count(map);
	}
	
	@Override
	public int save(FileDO sysFile){
		return fileDao.save(sysFile);
	}
	
	@Override
	public int update(FileDO sysFile){
		return fileDao.update(sysFile);
	}
	
	@Override
	public int remove(Long id){
		return fileDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return fileDao.batchRemove(ids);
	}

    @Override
    public Boolean isExist(String url) {
		Boolean isExist = false;
		if (!StringUtils.isEmpty(url)) {
			String filePath = url.replace("/files/", "");
			filePath = justdoConfig.getUploadPath() + filePath;
			File file = new File(filePath);
			if (file.exists()) {
				isExist = true;
			}
		}
		return isExist;
	}
	}
