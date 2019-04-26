package com.justdo.system.operationlog.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.operationlog.domain.OperationLogDO;
import com.justdo.system.operationlog.service.OperationLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统操作日志
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-17 14:19:50
 */

@Controller
@RequestMapping("/system/operationlog")
public class OperationLogController {

	private String preUrl="system/operationlog"  ;
	@Autowired
	private OperationLogService operationLogService;



	/**
	 * 列表页面
	 * @return 列表页面路径
	 */
	@GetMapping()
	@RequiresPermissions("system:operationlog:list")
	String OperationLog(){
		return preUrl + "/operationlog";
	}


	/**
	 * 列表数据
	 * @param params
	 * @return
	 */
	@Log("系统操作日志列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:operationlog:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<OperationLogDO> operationLogList = operationLogService.list(query);
		int total = operationLogService.count(query);
		PageUtils pageUtils = new PageUtils(operationLogList, total);
		return pageUtils;
	}

	/**
	 * 详情页面
	 * @param id
	 * @return 详情页面路径
	 */
	@GetMapping("/view/{id}")
	@RequiresPermissions("system:operationlog:view")
	String view(@PathVariable("id") String id,Model model){
		OperationLogDO operationLog = operationLogService.get(id);
		model.addAttribute("operationLog", operationLog);
		return preUrl + "/view";
	}
	/**
	 * 删除
	 * @param id
	 * @return R
	 */
	@Log("系统操作日志删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:operationlog:del")
	public R remove(String id){
		if(operationLogService.del(id)>0){
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return R
	 */
	@Log("系统操作日志批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:operationlog:batchDel")
	public R remove(@RequestParam("ids[]") String[] ids){
		operationLogService.batchDel(ids);
		return R.ok();
	}

}
