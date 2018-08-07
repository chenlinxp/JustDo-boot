package com.justdo.system.city.controller;

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

import com.justdo.system.city.domain.CityDO;
import com.justdo.system.city.service.CityService;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;

/**
 * 城市编码信息
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:33:37
 */
 
@Controller
@RequestMapping("/system/city")
public class CityController {
	@Autowired
	private CityService cityService;
	
	@GetMapping()
	@RequiresPermissions("system:city:city")
	String City(){
	    return "system/city/city";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:city:city")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CityDO> cityList = cityService.list(query);
		int total = cityService.count(query);
		PageUtils pageUtils = new PageUtils(cityList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:city:add")
	String add(){
	    return "system/city/add";
	}

	@GetMapping("/edit/{cityid}")
	@RequiresPermissions("system:city:edit")
	String edit(@PathVariable("cityid") String cityid,Model model){
		CityDO city = cityService.get(cityid);
		model.addAttribute("city", city);
	    return "system/city/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:city:add")
	public R save( CityDO city){
		if(cityService.save(city)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:city:edit")
	public R update( CityDO city){
		cityService.update(city);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:city:del")
	public R remove( String cityid){
		if(cityService.del(cityid)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:city:batchDel")
	public R remove(@RequestParam("ids[]") String[] cityids){
		cityService.batchDel(cityids);
		return R.ok();
	}
	
}
