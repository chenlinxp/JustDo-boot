package com.justdo.system.generator.controller;

import com.alibaba.fastjson.JSON;
import com.justdo.common.utils.R;
import com.justdo.system.generator.service.GeneratorService;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.justdo.common.utils.GeneratorCodeUtils.getConfig;


/**
 * 代码生成
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@RequestMapping("/system/generator")
@Controller
public class GeneratorController {

	String preUrl = "system/generator";

	@Autowired
	GeneratorService generatorService;

	/**
	 * 列表页面
	 * @return 列表页面路径
	 */
	@RequiresPermissions("system:generator:list")
	@GetMapping()
	String generator() {
		return preUrl + "/list";
	}

	/**
	 * 列表数据
	 * @return 列表数据
	 */
	@RequiresPermissions("system:generator:list")
	@ResponseBody
	@GetMapping("/list")
	List<Map<String, Object>> list() {
		List<Map<String, Object>> list = generatorService.list();
		return list;
	};

	/**
	 * @param tableName
	 * 生成单表代码
	 */
	@RequiresPermissions("system:generator:code")
	@GetMapping("/settingCode/{tableName}")
	String settingCode(@PathVariable("tableName") String tableName ,Model model)  {
		model.addAttribute("tablename", tableName);
		return preUrl + "/settingcode";
	}

	/**
	 * @param tableName
	 * 单表的列展示
	 */
	//@RequiresPermissions("system:generator:code")
	@GetMapping("/getGeneratorColumns/{tableName}")
	@ResponseBody
	List<Map<String, String>> getGeneratorColumns(@PathVariable("tableName") String tableName)  {
	List<Map<String, String>> columnsMap = null;
	List<Map<String, String>> list =  new ArrayList<Map<String, String>>();
		//配置信息
		Configuration config = getConfig();
		try{
			columnsMap = generatorService.getGeneratorColumns(tableName);
			if(columnsMap == null) {
				return list;
			}else {
				for(Map<String,String> a : columnsMap){
					//列的数据类型，转换成Java类型
					String attrType = config.getString(a.get("dataType"), "unknowType");
					a.put("dataType",attrType);
				    a.put("displayType","001");
					a.put("searchType","001");
					a.put("orderNum","0");
					list.add(a);
					}
				}
		}catch(Exception e){
			e.printStackTrace();
			return  list;
		}
		return list;
	}
	/**
	 * @param tableName
	 * 生成单表代码
	 */
	@RequiresPermissions("system:generator:code")
	@PostMapping("/code")
	public void code(HttpServletRequest request, HttpServletResponse response,
	                 @RequestParam("alltabledata") String alltabledata,@RequestParam("tableName") String tableName ) throws IOException {
		String[] tableNames = new String[] { tableName };
		byte[] tabledata = generatorService.generatorCode(tableNames,alltabledata);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"justdo.zip\"");
		response.addHeader("Content-Length", "" + tabledata.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(tabledata, response.getOutputStream());
	}

	/**
	 * @param tables　
	 * 生成多表代码
	 */
	@RequiresPermissions("system:generator:batchCode")
	@GetMapping("/batchCode")
	public void batchCode(HttpServletRequest request, HttpServletResponse response, String tables) throws IOException {
		String[] tableNames = new String[] {};
		tableNames = JSON.parseArray(tables).toArray(tableNames);
		byte[] data = generatorService.generatorCode(tableNames);
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"justdo.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");

		IOUtils.write(data, response.getOutputStream());
	}

	/**
	 * 编辑代码生成策略
	 * @return 代码生成策略页面
	 */
	@RequiresPermissions("system:generator:edit")
	@GetMapping("/edit")
	public String edit(Model model) {
		Configuration conf = getConfig();

		Map<String, Object> property = new HashMap<>(10);
		property.put("projectName", conf.getProperty("projectName"));
		property.put("author", conf.getProperty("author"));

		property.put("phone", conf.getProperty("phone"));
		property.put("email", conf.getProperty("email"));

		property.put("package", conf.getProperty("package"));
		property.put("versionCode", conf.getProperty("versionCode"));

		property.put("autoRemovePre", conf.getProperty("autoRemovePre"));
		property.put("tablePrefix", conf.getProperty("tablePrefix"));

		model.addAttribute("property", property);
		return preUrl + "/edit";
	}

	/**
	 * 更新代码生成策略
	 * @return 代码生成策略
	 */
	@RequiresPermissions("system:generator:edit")
	@ResponseBody
	@PostMapping("/update")
	R update(@RequestParam Map<String, Object> map) {
		try {
			PropertiesConfiguration conf = new PropertiesConfiguration("generator.properties");
			conf.setProperty("projectName", map.get("projectName"));
			conf.setProperty("author", map.get("author"));

			conf.setProperty("email", map.get("email"));
			conf.setProperty("phone", map.get("phone"));

			conf.setProperty("package", map.get("package"));
			conf.setProperty("versionCode", map.get("versionCode"));

			conf.setProperty("autoRemovePre", map.get("autoRemovePre"));
			conf.setProperty("tablePrefix", map.get("tablePrefix"));
			conf.save();
		} catch (ConfigurationException e) {
			return R.error("保存配置文件出错");
		}
		return R.ok();
	}
}
