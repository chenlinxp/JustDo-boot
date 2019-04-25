package com.justdo.system.role.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.utils.R;
import com.justdo.system.role.domain.RoleDO;
import com.justdo.system.role.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;



/**
 * 角色
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@RequestMapping("/system/role")
@Controller
public class RoleController extends BaseController {
	String preUrl = "system/role";
	@Autowired
	RoleService roleService;

	@RequiresPermissions("system:role:list")
	@GetMapping()
	String role() {
		return preUrl + "/role";
	}

	@Log("角色列表")
	@RequiresPermissions("system:role:list")
	@GetMapping("/list")
	@ResponseBody()
	List<RoleDO> list() {
		List<RoleDO> roles = roleService.list(new HashMap<>(1));
		return roles;
	}

	@Log("添加角色")
	@RequiresPermissions("system:role:add")
	@GetMapping("/add")
	String add() {
		return preUrl + "/add";
	}

	@Log("编辑角色")
	@RequiresPermissions("system:role:edit")
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id, Model model) {
		RoleDO roleDO = roleService.get(id);
		model.addAttribute("role", roleDO);
		return preUrl + "/edit";
	}

	@Log("新增角色")
	@RequiresPermissions("system:role:add")
	@PostMapping("/save")
	@ResponseBody()
	R save(RoleDO role) {
		Date nowdate =	new Date();
		role.setCreateTime(nowdate);
		role.setModifyTime(nowdate);
		if (roleService.save(role) > 0) {
			return R.ok();
		} else {
			return R.error(1, "新增角色失败");
		}
	}

	@Log("更新角色")
	@RequiresPermissions("system:role:edit")
	@PostMapping("/update")
	@ResponseBody()
	R update(RoleDO role) {
		role.setModifyTime(new Date());
		if (roleService.update(role) > 0) {
			return R.ok();
		} else {
			return R.error(1, "更新角色失败");
		}
	}

	@Log("删除角色")
	@RequiresPermissions("system:role:del")
	@PostMapping("/del")
	@ResponseBody()
	R save(String id) {
		if (roleService.del(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除角色失败");
		}
	}
	
	@RequiresPermissions("system:role:batchDel")
	@Log("批量删除角色")
	@PostMapping("/batchDel")
	@ResponseBody
	R batchDel(@RequestParam("ids[]") String[] ids) {
		int r = roleService.batchDel(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error(1, "批量删除角色失败");
	}
}
