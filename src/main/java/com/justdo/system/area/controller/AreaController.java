package com.justdo.system.area.controller;

import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.area.domain.AreaDO;
import com.justdo.system.area.service.AreaService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 地区编码表
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:33:49
 */
 
@Controller
@RequestMapping("/system/area")
public class  AreaController {
	@Autowired
	private AreaService areaService;
	
	@GetMapping()
	@RequiresPermissions("system:area:list")
	String Area(){
	    return "system/area/area";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:area:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AreaDO> areaList = areaService.list(query);
		int total = areaService.count(query);
		PageUtils pageUtils = new PageUtils(areaList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:area:add")
	String add(){
	    return "system/area/add";
	}

	@GetMapping("/edit/{areaid}")
	@RequiresPermissions("system:area:edit")
	String edit(@PathVariable("areaid") String areaid,Model model){
		AreaDO area = areaService.get(areaid);
		model.addAttribute("area", area);
	    return "system/area/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:area:add")
	public R save( AreaDO area){
		if(areaService.save(area)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:area:edit")
	public R update( AreaDO area){
		areaService.update(area);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:area:del")
	public R remove( String areaid){
		if(areaService.del(areaid)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:area:batchDel")
	public R remove(@RequestParam("ids[]") String[] areaids){
		areaService.batchDel(areaids);
		return R.ok();
	}
	
}
