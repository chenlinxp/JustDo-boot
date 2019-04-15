package com.justdo.system.organ.controller;

import com.justdo.common.domain.Tree;
import com.justdo.common.domain.TreeNode;
import com.justdo.common.utils.R;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.service.OrganService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
	private String prefix = "system/organ";
	@Autowired
	private OrganService organService;

	
	@GetMapping()
	@RequiresPermissions("system:organ:list")
	String Organ(){
	    return "system/organ/organ";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:organ:list")
	public List<OrganDO> list(@RequestParam Map<String, Object> params){
		List<OrganDO> deptList = organService.list(params);
		return deptList;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("system:organ:add")
	String add(){
	    return prefix+"/add";
	}

	@GetMapping("/edit/{organid}")
	@RequiresPermissions("system:organ:edit")
	String edit(@PathVariable("organid") String organid,Model model){
		OrganDO organ = organService.get(organid);
		model.addAttribute("organ", organ);
	    return prefix+"/edit";
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
		if(organ.getOrganpid()==organ.getOrganid()) {
			return R.error();
		}
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
		if(organService.del(organid)>0){
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

	@GetMapping("/tree")
	@ResponseBody
	public Tree<OrganDO> tree() {
		Tree<OrganDO> tree = new Tree<OrganDO>();
		tree = organService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/organTree";
	}

	@GetMapping("/organdept")
	@ResponseBody
	public List<TreeNode> findOrganDept(@RequestParam Map<String, Object> params){

		List<TreeNode> list=organService.getOrgans(params);

		return list;
	}
}
