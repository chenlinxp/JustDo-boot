package com.justdo.system.regexp.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.justdo.system.regexp.domain.RegexpEXDO;
import com.justdo.system.regexp.domain.RegexpDO;
import com.justdo.system.regexp.service.RegexpService;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;

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
	@RequiresPermissions("system:regexp:regexp")
	String Regexp(){
	    return "system/regexp/regexp";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:regexp:regexp")
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
		if(regexpService.save(regexp)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:regexp:edit")
	public R update( RegexpDO regexp){
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
		if(regexpService.remove(rid)>0){
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
