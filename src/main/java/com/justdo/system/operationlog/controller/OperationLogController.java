package com.justdo.system.operationlog.controller;

import com.justdo.common.domain.PageDO;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.operationlog.domain.OperationLogDO;
import com.justdo.system.operationlog.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@RequestMapping("/system/operationlog")
@Controller
public class OperationLogController {
	@Autowired
	OperationLogService operationLogService;
	String prefix = "system/operationlog";

	@GetMapping()
	String operationlog() {
		return prefix + "/operationlog";
	}

	@ResponseBody
	@GetMapping("/list")
	PageDO<OperationLogDO> list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		PageDO<OperationLogDO> page = operationLogService.queryList(query);
		return page;
	}
	
	@ResponseBody
	@PostMapping("/del")
	R del(String id) {
		if (operationLogService.del(id)>0) {
			return R.ok();
		}
		return R.error();
	}

	@ResponseBody
	@PostMapping("/batchDel")
	R batchDel(@RequestParam("ids[]") String[] ids) {
		int r = operationLogService.batchDel(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}
}
