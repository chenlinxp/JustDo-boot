
package com.justdo.system.employee.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.domain.Tree;
import com.justdo.common.utils.MD5Utils;
import com.justdo.common.utils.R;
import com.justdo.common.utils.ShiroUtils;
import com.justdo.common.utils.StringUtils;
import com.justdo.system.employee.service.EmployeeService;
import com.justdo.system.file.service.FileService;
import com.justdo.system.resource.domain.ResourceDO;
import com.justdo.system.resource.service.ResourceService;
import com.justdo.system.role.domain.RoleDO;
import com.justdo.system.role.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 员工登录
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Controller
public class ELoginController extends BaseController {

	@Autowired
	ResourceService resourceService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	FileService fileService;
	@Autowired
	RoleService roleService;
	@GetMapping({ "/", "" })
	String welcome(Model model) {
		model.addAttribute("title", "登录");
		model.addAttribute("keywords", "JutsDo");
		model.addAttribute("description", "JutsDo登录");
		return "redirect:/login";
	}

	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<ResourceDO>> resourceDOs = resourceService.listEmployeeResourceTree(getEmployeeId());
		model.addAttribute("resources", resourceDOs);
		model.addAttribute("name", getSimpleEmployee().getLoginName());
		model.addAttribute("userid", getSimpleEmployee().getEmployeeId());

		//EmployeeDO employee = employeeService.get(getEmployeeId());
		String roleId = employeeService.getRoleId(getEmployeeId());
		RoleDO roleDO = roleService.get(roleId);
		String roleName = "";
		if(roleDO!=null) {
			roleName = roleDO.getRoleName();
		}
		//FileDO fileDO = fileService.get(getSimpleEmployee().getPhotoId());
		String photoUrl = getSimpleEmployee().getPhotoUrl();
		if(StringUtils.isNotEmpty(photoUrl)){
			model.addAttribute("picUrl",photoUrl);
		}else {

			model.addAttribute("picUrl","/img/a9.jpg");
		}
		model.addAttribute("username", getSimpleEmployee().getLoginName());
		model.addAttribute("rolename", roleName);
		return "index";
	}

	@GetMapping("/login")
	String login(Model model) {
		model.addAttribute("title", "登录");
		model.addAttribute("keywords", "JutsDo");
		model.addAttribute("description", "JutsDo登录");
		return "login";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password, String rememberme) {
		String loginName = username.trim();
		Map<String ,Object> params = new HashMap<>(1);
		params.put("loginName",loginName);
		if(employeeService.exist(params)) {
			String salt = employeeService.getPasswordSalt(loginName);
			String password2 = MD5Utils.encrypt("md5",password,salt);
			String a = password2.toString();//4d9573ec9f4cf8978543486c9e9eb681
//			password = MD5Utils.encrypt(salt, password);  //fe02dde7415743a285fc5cefa6942ffc
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser.isAuthenticated() && currentUser.isRemembered()) {
				return R.ok();
			} else {
				UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
				if (rememberme != null) {
					token.setRememberMe(true);
				}
				try {
					currentUser.login(token);
					return R.ok();
				} catch (AuthenticationException e) {
					System.out.println(e.toString());
					return R.error("用户名或密码错误");
				}
			}
		}
		else
		{
			return R.error("用户名或密码错误");
		}
	}

	@Log("退出")
	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

}
