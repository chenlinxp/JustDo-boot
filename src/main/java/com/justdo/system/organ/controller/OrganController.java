package com.justdo.system.organ.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.domain.Tree;
import com.justdo.common.domain.TreeNode;
import com.justdo.common.utils.R;
import com.justdo.system.dept.service.DeptService;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.service.OrganService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
	private String preUrl = "system/organ";
	@Autowired
	private OrganService organService;

	@Autowired
	private DeptService deptService;

	
	@GetMapping()
	@RequiresPermissions("system:organ:list")
	String Organ(){
	    return "system/organ/organ";
	}

	@Log("机构列表")
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
	    return preUrl+"/add";
	}

	@GetMapping("/edit/{organid}")
	@RequiresPermissions("system:organ:edit")
	String edit(@PathVariable("organid") String organid,Model model){
		OrganDO organ = organService.get(organid);
		model.addAttribute("organ", organ);
	    return preUrl+"/edit";
	}
	
	/**
	 * 保存
	 */
	@Log("增加机构")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:organ:add")
	public R save( OrganDO organ){
		if(organService.save(organ)>0){
			return R.ok();
		}
		return R.error("新增机构失败！");
	}
	/**
	 * 修改
	 */
	@Log("编辑机构")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:organ:edit")
	public R update( OrganDO organ) {
		if (organ.getOrganpid() == organ.getOrganid()) {
			return R.error("上级机构不能选自己！");
		}
		if (organService.update(organ) > 0) {
			return R.ok();
		}
		return R.error("更新机构失败！");

	}
	/**
	 * 删除
	 */
	@Log("删除机构")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:organ:del")
	public R remove( String organid){

		Map<String,Object> map = new HashMap<>();
		map.put("organid",organid);

		if(deptService.count(map) == 0) {
			if (organService.del(organid) > 0) {
				return R.ok();
			}
			return R.error("删除机构失败！");
		}
		else {
			return R.error("此机构下有部门不能删除！");
		}
	}
	
	/**
	 * 批量删除
	 */
	@Log("批量删除机构")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:organ:batchDel")
	public R remove(@RequestParam("ids[]") String[] organids){
		if(organService.batchDel(organids)>0){
			return R.ok();
		}
		return R.error("删除机构失败！");
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
		return  preUrl + "/organTree";
	}

	@GetMapping("/organdept")
	@ResponseBody
	public List<TreeNode> findOrganDept(@RequestParam Map<String, Object> params){

		List<TreeNode> list=organService.getOrgans(params);

		return list;
	}
}
