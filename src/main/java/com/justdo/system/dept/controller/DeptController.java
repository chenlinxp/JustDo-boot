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

import java.util.Date;
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
	
	private String preUrl = "system/dept";
	@Autowired
	private DeptService deptService;
	@Autowired
	private OrganService organService;


	@GetMapping()
	@RequiresPermissions("system:dept:list")
	@ApiOperation(value="获取部门列表界面", notes="获取部门列表界面")
	String dept() {
		return preUrl + "/dept";
	}


	@Log("部门列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:dept:list")
	@ApiOperation(value="获取部门列表接口", notes="获取部门列表接口")
	public List<DeptDO> list(@RequestParam Map<String, Object> params) {
		List<DeptDO> deptList = deptService.list(params);
		return deptList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("system:dept:add")
	@ApiOperation(value="新增部门界面", notes="新增部门界面")
	String add(@PathVariable("pId") String pId, Model model) {
		model.addAttribute("deptpid", pId);
		if (!StringUtils.isNotEmpty(pId)) {
			model.addAttribute("deptpname", deptService.get(pId).getDeptname());
		} else {
			model.addAttribute("deptpname", "总部门");
		}
		return  preUrl + "/add";
	}

	@GetMapping("/add")
	@RequiresPermissions("system:dept:add")
	@ApiOperation(value="新增部门界面", notes="新增部门界面")
	String add(Model model) {
		return  preUrl + "/add";
	}

	@GetMapping("/edit/{deptId}")
	@RequiresPermissions("system:dept:edit")
	@ApiOperation(value="编辑部门界面", notes="编辑部门界面")
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
		return  preUrl + "/edit";
	}

	/**
	 * 新增部门
	 */
	@Log("新增部门")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:dept:add")
	@ApiOperation(value="新增部门接口", notes="新增部门接口")
	public R save(DeptDO dept) {
		Date nowdate =	new Date();
		dept.setCreateTime(nowdate);
		dept.setModifyTime(nowdate);
		if (deptService.save(dept) > 0) {
			return R.ok();
		}
		return R.error("新增部门失败！");
	}

	/**
	 * 修改部门
	 */
	@Log("编辑部门")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:dept:edit")
	@ApiOperation(value="编辑部门接口", notes="编辑部门接口")
	public R update(DeptDO dept) {
		dept.setModifyTime(new Date());
		if (deptService.update(dept) > 0) {
			return R.ok();
		}
		return R.error("更新部门失败！");
	}

	/**
	 * 删除部门
	 */
	@Log("删除部门")
	@PostMapping("/del")
	@ResponseBody
	@RequiresPermissions("system:dept:del")
	@ApiOperation(value="删除部门接口", notes="删除部门接口")
	public R remove(String deptId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("deptpid", deptId);
		if(deptService.count(map)>0) {
			return R.error(1, "包含下级部门,不允许修改");
		}
		if(deptService.checkDeptHasEmployee(deptId)) {
			if (deptService.del(deptId) > 0) {
				return R.ok();
			}
		}else {
			return R.error(1, "部门包含用户,不允许修改！");
		}
		return R.error(1,"删除部门失败");
	}

	/**
	 * 批量删除部门
	 */
	@Log("批量删除部门")
	@PostMapping("/batchDel")
	@ResponseBody
	@RequiresPermissions("system:dept:batchDel")
	@ApiOperation(value="批量删除部门接口", notes="批量删除部门接口")
	public R remove(@RequestParam("ids[]") String[] deptIds) {
		if (deptService.batchDel(deptIds)>0) {
			return R.ok();
			}
		return R.error(1,"批量删除部门失败");
	}

	@GetMapping("/tree/{organId}")
	@ResponseBody
	@ApiOperation(value="根据organId获取部门树接口", notes="根据organId获取部门树接口")
	public Tree<DeptDO> tree(@PathVariable("organId") String organId) {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		Map<String,Object> map = new HashMap<String,Object>(1);
		if(StringUtils.isNotEmpty(organId)&&!organId.equals("0")){
		map.put("organid",organId);}
		tree = deptService.getTree(map);
		return tree;
	}
	@GetMapping("/treeView/{organId}")
	@ApiOperation(value="根据organId获取部门树界面", notes="根据organId获取部门树界面")
	String treeView(@PathVariable("organId") String organId, Model model) {
		model.addAttribute("organId", organId);
		return  preUrl + "/deptTree";
	}

}
