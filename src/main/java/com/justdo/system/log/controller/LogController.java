package com.justdo.system.log.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.tools.cache.AsynchronousFileCacheBacking.RemoveCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.justdo.system.log.domain.LogDO;
import com.justdo.common.domain.PageDO;
import com.justdo.system.log.service.LogService;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;

/**
 * 系统日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@RequestMapping("/system/log")
@Controller
public class LogController {
	@Autowired
	LogService logService;
	String prefix = "system/log";

	@GetMapping()
	String log() {
		return prefix + "/log";
	}

	@ResponseBody
	@GetMapping("/list")
	PageDO<LogDO> list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		PageDO<LogDO> page = logService.queryList(query);
		return page;
	}
	
	@ResponseBody
	@PostMapping("/del")
	R remove(Long id) {
		if (logService.remove(id)>0) {
			return R.ok();
		}
		return R.error();
	}

	@ResponseBody
	@PostMapping("/batchDel")
	R batchDel(@RequestParam("ids[]") Long[] ids) {
		int r = logService.batchDel(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}
}
