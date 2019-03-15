package com.justdo.system.errorlog.controller;

import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.errorlog.domain.ErrorLogDO;
import com.justdo.system.errorlog.service.ErrorLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统错误日志
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-14 18:08:46
 */
 
@Controller
@RequestMapping("/system/errorlog")
public class ErrorLogController {
	@Autowired
	private ErrorLogService errorLogService;
	
	@GetMapping()
	@RequiresPermissions("system:errorlog:errorlog")
	String ErrorLog(){
	    return "system/errorlog/errorlog";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:errorlog:errorlog")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ErrorLogDO> errorLogList = errorLogService.list(query);
		int total = errorLogService.count(query);
		PageUtils pageUtils = new PageUtils(errorLogList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:errorlog:add")
	String add(){
	    return "system/errorlog/add";
	}

	@GetMapping("/edit/{errorlogId}")
	@RequiresPermissions("system:errorlog:edit")
	String edit(@PathVariable("errorlogId") String errorlogId,Model model){
		ErrorLogDO errorLog = errorLogService.get(errorlogId);
		model.addAttribute("errorLog", errorLog);
	    return "system/errorlog/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:errorlog:add")
	public R save( ErrorLogDO errorLog){
		if(errorLogService.save(errorLog)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:errorlog:edit")
	public R update( ErrorLogDO errorLog){
		errorLogService.update(errorLog);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:errorlog:del")
	public R remove( String errorlogId){
		if(errorLogService.del(errorlogId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:errorlog:batchDel")
	public R remove(@RequestParam("ids[]") String[] errorlogIds){
		errorLogService.batchDel(errorlogIds);
		return R.ok();
	}
	
}
