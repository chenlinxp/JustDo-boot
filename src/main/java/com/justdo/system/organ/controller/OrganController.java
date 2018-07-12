package com.justdo.system.organ.controller;

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

import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.service.OrganService;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;

/**
 * 机构
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-07-06 15:08:01
 */
 
@Controller
@RequestMapping("/system/organ")
public class OrganController {
	@Autowired
	private OrganService organService;
	
	@GetMapping()
	@RequiresPermissions("system:organ:organ")
	String Organ(){
	    return "system/organ/organ";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:organ:organ")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<OrganDO> organList = organService.list(query);
		int total = organService.count(query);
		PageUtils pageUtils = new PageUtils(organList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:organ:add")
	String add(){
	    return "system/organ/add";
	}

	@GetMapping("/edit/{organid}")
	@RequiresPermissions("system:organ:edit")
	String edit(@PathVariable("organid") String organid,Model model){
		OrganDO organ = organService.get(organid);
		model.addAttribute("organ", organ);
	    return "system/organ/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:organ:add")
	public R save( OrganDO organ){
		if(organService.save(organ)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:organ:edit")
	public R update( OrganDO organ){
		organService.update(organ);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:organ:del")
	public R remove( String organid){
		if(organService.remove(organid)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:organ:batchDel")
	public R remove(@RequestParam("ids[]") String[] organids){
		organService.batchDel(organids);
		return R.ok();
	}
	
}
