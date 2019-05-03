
package com.justdo.system.generator.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 代码生成
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public interface GeneratorService {

	List<Map<String, Object>> list();

	byte[] generatorCode(String[] tableNames);

	byte[] generatorCode(String[] tableNames,String allTableData);

	/**
	 * 获取表的列List<Map>
	 * @param tablename
	 * @return
	 */
	List<Map<String, String>> getGeneratorColumns(String tablename);
}
