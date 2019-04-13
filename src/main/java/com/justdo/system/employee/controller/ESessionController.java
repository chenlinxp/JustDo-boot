package com.justdo.system.employee.controller;


import com.justdo.common.utils.R;
import com.justdo.system.employee.domain.EmployeeOnline;
import com.justdo.system.employee.service.ESessionService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	public String online() {
		return "system/online/online";
	}

	@ResponseBody
	@RequestMapping("/list")
	public List<EmployeeOnline> list() {
		return esessionService.list();
	}

	@ResponseBody
	@RequestMapping("/forceLogout/{sessionId}")
	public R forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		try {
			esessionService.forceLogout(sessionId);
			return R.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}

	}

	@ResponseBody
	@RequestMapping("/sessionList")
	public Collection<Session> sessionList() {
		return esessionService.sessionList();
	}

}
