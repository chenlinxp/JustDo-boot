package com.justdo.system.dept.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.domain.Tree;
import com.justdo.common.utils.R;
import com.justdo.common.utils.StringUtils;
import com.justdo.system.dept.domain.DeptDO;
import com.justdo.system.dept.service.DeptService;
import com.justdo.system.organ.domain.OrganDO;
import com.justdo.system.organ.service.OrganService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * 部门管理
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {
	private String prefix = "system/dept";
	@Autowired
	private DeptService deptService;
	@Autowired
	private OrganService organService;
	@GetMapping()
	@RequiresPermissions("system:dept:list")
	String dept() {
		return prefix + "/dept";
	}

	@ApiOperation(value="获取部门列表", notes="")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:dept:list")
	public List<DeptDO> list() {
		Map<String, Object> query = new HashMap<>(16);
		List<DeptDO> deptList = deptService.list(query);
		return deptList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("system:dept:add")
	String add(@PathVariable("pId") String pId, Model model) {
		model.addAttribute("deptpid", pId);
		if (!StringUtils.isNotEmpty(pId)) {
			model.addAttribute("deptpname", deptService.get(pId).getDeptname());
		} else {
			model.addAttribute("deptpname", "总部门");
		}
		return  prefix + "/add";
	}

	@GetMapping("/add")
	@RequiresPermissions("system:dept:add")
	String add(Model model) {
		return  prefix + "/add";
	}

	@GetMapping("/edit/{deptId}")
	@RequiresPermissions("system:dept:edit")
	String edit(@PathVariable("deptId") String deptId, Model model) {
		DeptDO dept = deptService.get(deptId);

		if(StringUtils.isNotEmpty(dept.getDeptpid())) {
			DeptDO parDept = deptService.get(dept.getDeptpid());
            if(parDept!=null){
				dept.setDeptpname(parDept.getDeptname());
			}
		}
		if(StringUtils.isNotEmpty(dept.getOrganid())){
			OrganDO organDO = organService.get(dept.getOrganid());
			if(organDO!=null){
				dept.setOrganname(organDO.getOrganname());
			}
		}

		model.addAttribute("dept", dept);
		return  prefix + "/edit";
	}

	/**
	 * 新增部门
	 */
	@Log("新增部门")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:dept:add")
	public R save(DeptDO dept) {
		if (deptService.save(dept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改部门
	 */
	@Log("修改部门")
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("system:dept:edit")
	public R update(DeptDO dept) {
		if (deptService.update(dept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除部门
	 */
	@Log("删除部门")
	@PostMapping("/del")
	@ResponseBody
	@RequiresPermissions("system:dept:del")
	public R remove(String deptId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", deptId);
		if(deptService.count(map)>0) {
			return R.error(1, "包含下级部门,不允许修改");
		}
		if(deptService.checkDeptHasUser(deptId)) {
			if (deptService.del(deptId) > 0) {
				return R.ok();
			}
		}else {
			return R.error(1, "部门包含用户,不允许修改！");
		}
		return R.error();
	}

	/**
	 * 批量删除部门
	 */
	@Log("删除部门")
	@PostMapping("/batchDel")
	@ResponseBody
	@RequiresPermissions("system:dept:batchDel")
	public R remove(@RequestParam("ids[]") String[] deptIds) {
		deptService.batchDel(deptIds);
		return R.ok();
	}

	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = deptService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/deptTree";
	}

}
