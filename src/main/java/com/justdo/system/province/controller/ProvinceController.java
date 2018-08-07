package com.justdo.system.province.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.justdo.system.province.domain.ProvinceDO;
import com.justdo.system.province.service.ProvinceService;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;

/**
 * 省份编码表
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:20:40
 */
 
@Controller
@RequestMapping("/system/province")
public class ProvinceController {
	@Autowired
	private ProvinceService provinceService;
	
	@GetMapping()
	@RequiresPermissions("system:province:province")
	String Province(){
	    return "system/province/province";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:province:province")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ProvinceDO> provinceList = provinceService.list(query);
		int total = provinceService.count(query);
		PageUtils pageUtils = new PageUtils(provinceList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:province:add")
	String add(){
	    return "system/province/add";
	}

	@GetMapping("/edit/{provinceid}")
	@RequiresPermissions("system:province:edit")
	String edit(@PathVariable("provinceid") String provinceid,Model model){
		ProvinceDO province = provinceService.get(provinceid);
		model.addAttribute("province", province);
	    return "system/province/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:province:add")
	public R save( ProvinceDO province){
		if(provinceService.save(province)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("system:province:edit")
	public R update( ProvinceDO province){
		provinceService.update(province);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:province:del")
	public R remove( String provinceid){
		if(provinceService.del(provinceid)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:province:batchDel")
	public R remove(@RequestParam("ids[]") String[] provinceids){
		provinceService.batchDel(provinceids);
		return R.ok();
	}
	
}
