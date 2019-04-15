package com.justdo.system.city.controller;

import com.justdo.common.utils.R;
import com.justdo.system.city.domain.CityDO;
import com.justdo.system.city.service.CityService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
	@RequiresPermissions("system:city:list")
	String City(){
	    return "system/city/city";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:province:list")
	public List<CityDO> list(@RequestParam Map<String, Object> params){
		return cityService.list(params);
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
