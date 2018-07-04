
package com.justdo.system.generator.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
}
