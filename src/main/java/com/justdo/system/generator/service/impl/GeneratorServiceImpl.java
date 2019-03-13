package com.justdo.system.generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justdo.system.generator.dao.GeneratorDao;
import com.justdo.system.generator.service.GeneratorService;
import com.justdo.common.utils.GeneratorCodeUtils;

/**
 * 代码生成
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {
	@Autowired
	GeneratorDao generatorMapper;

	@Override
	public List<Map<String, Object>> list() {
		List<Map<String, Object>> list = generatorMapper.list();
		return list;
	}

	@Override
	public byte[] generatorCode(String[] tableNames) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for(String tableName : tableNames){
			//查询表信息
			Map<String, String> table = generatorMapper.get(tableName);
			//查询列信息
			List<Map<String, String>> columns = generatorMapper.listColumns(tableName);
			//生成代码
			GeneratorCodeUtils.generatorCode(table, columns, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

}
