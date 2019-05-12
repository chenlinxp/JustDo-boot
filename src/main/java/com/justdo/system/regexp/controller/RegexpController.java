package com.justdo.system.regexp.controller;

import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.regexp.domain.RegexpDO;
import com.justdo.system.regexp.service.RegexpService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统正则表达式
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-06-26 13:13:39
 */
 
@Controller
@RequestMapping("/system/regexp")
public class RegexpController {
	@Autowired
	private RegexpService regexpService;
	
	@GetMapping()
	@RequiresPermissions("system:regexp:list")
	String Regexp(){
	    return "system/regexp/regexp";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:regexp:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<RegexpDO> regexpList = regexpService.list(query);
		int total = regexpService.count(query);
		PageUtils pageUtils = new PageUtils(regexpList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:regexp:add")
	String add(){
	    return "system/regexp/add";
	}

	@GetMapping("/edit/{rid}")
	@RequiresPermissions("system:regexp:edit")
	String edit(@PathVariable("rid") String rid,Model model){
		RegexpDO regexp = regexpService.get(rid);
		model.addAttribute("regexp", regexp);
	    return "system/regexp/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:regexp:add")
	public R save( RegexpDO regexp){
		String nowDateString = DateUtils.formatTimeNow("yyyy-MM-dd HH:mm:ss");
		regexp.setCreateTime(nowDateString);
		regexp.setModifyTime(nowDateString);
		if(regexpService.save(regexp)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:regexp:edit")
	public R update( RegexpDO regexp){
		String nowDateString = DateUtils.formatTimeNow("yyyy-MM-dd HH:mm:ss");
		regexp.setModifyTime(nowDateString);
		regexpService.update(regexp);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:regexp:del")
	public R remove( String rid){
		if(regexpService.del(rid)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:regexp:batchDel")
	public R remove(@RequestParam("ids[]") String[] rids){
		regexpService.batchDel(rids);
		return R.ok();
	}
	
}
