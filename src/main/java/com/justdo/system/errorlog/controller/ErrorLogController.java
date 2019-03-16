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
 * @date 2019-03-16 19:46:45
 */
 
@Controller
@RequestMapping("/system/errorlog")
public class ErrorLogController {
	@Autowired
	private ErrorLogService errorLogService;



	/**
	* 列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("system:errorlog:errorlog")
	String ErrorLog(){
	    return "system/errorlog/errorlog";
	}


	/**
	* 列表数据
	* @param params
	* @return
	*/
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

	/**
	* 详情页面
	* @param errorlogId
	* @return 列表页面路径
	*/
	@GetMapping("/view/{errorlogId}")
	@RequiresPermissions("system:errorLog:edit")
	String view(@PathVariable("errorlogId") String errorlogId,Model model){
			ErrorLogDO errorLog = errorLogService.get(errorlogId);
		model.addAttribute("errorLog", errorLog);
		return "system/errorlog/view";
	}

	/**
	* 增加页面
	* @return 增加页面路径
	*/
	@GetMapping("/add")
	@RequiresPermissions("system:errorlog:add")
	String add(){
	    return "system/errorlog/add";
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
	* 编辑页面
    * @param errorlogId
    * @return 编辑页面路径
    */
	@GetMapping("/edit/{errorlogId}")
	@RequiresPermissions("system:errorLog:edit")
	String edit(@PathVariable("errorlogId") String errorlogId,Model model){
		ErrorLogDO errorLog = errorLogService.get(errorlogId);
		model.addAttribute("errorLog", errorLog);
	    return "system/errorlog/edit";
	}
	

	/**
	 * 更新
	 * @param errorLog
	 * @return R
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
	 * @param errorlogId
	 * @return R
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
	 * 批量删除
	 * @param errorlogIds
	 * @return R
	 */
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:errorlog:batchDel")
	public R remove(@RequestParam("ids[]") String[] errorlogIds){
		errorLogService.batchDel(errorlogIds);
		return R.ok();
	}
	
}
