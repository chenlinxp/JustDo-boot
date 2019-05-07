package com.justdo.system.employee.controller;


import com.justdo.common.annotation.Log;
import com.justdo.common.utils.R;
import com.justdo.system.employee.domain.EmployeeOnline;
import com.justdo.system.employee.service.ESessionService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;

/**
 * 员工session管理
 */
@RequestMapping("/system/online")
@Controller
public class ESessionController {
	@Autowired
	ESessionService esessionService;

	@GetMapping()
	@RequiresPermissions("system:online:list")
	public String online() {
		return "system/online/online";
	}

	@Log("在线员工列表")
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("system:online:list")
	public List<EmployeeOnline> list() {
		return esessionService.list();
	}

	@Log("强制员工退出")
	@ResponseBody
	@RequestMapping("/forceLogout")
	@RequiresPermissions("system:online:forceLogout")
	public R forceLogout(@RequestParam("ids[]") String[] ids, RedirectAttributes redirectAttributes) {
		try {
			esessionService.forceLogout(ids);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}

	}

	@ResponseBody
	@RequestMapping("/sessionList")
	@RequiresPermissions("system:online:list")
	public Collection<Session> sessionList() {
		return esessionService.sessionList();
	}

}
