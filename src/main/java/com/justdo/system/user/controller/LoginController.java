package com.justdo.system.user.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.domain.Tree;
import com.justdo.common.utils.MD5Utils;
import com.justdo.common.utils.R;
import com.justdo.common.utils.ShiroUtils;
import com.justdo.system.file.domain.FileDO;
import com.justdo.system.file.service.FileService;
import com.justdo.system.menu.domain.MenuDO;
import com.justdo.system.menu.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;



/**
 * 登录
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Controller
public class LoginController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;
	@Autowired
	FileService fileService;
	@GetMapping({ "/", "" })
	String welcome(Model model) {
		model.addAttribute("title", "登录");
		model.addAttribute("keywords", "JutsDo");
		model.addAttribute("description", "JutsDo登录");
		return "redirect:/login";
	}

	@Log("访问首页")
	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		FileDO fileDO = fileService.get(getUser().getPicId());
		if(fileDO!=null&&fileDO.getUrl()!=null){
			if(fileService.isExist(fileDO.getUrl())){
				model.addAttribute("picUrl",fileDO.getUrl());
			}else {
				model.addAttribute("picUrl","/img/photo_s.jpg");
			}
		}else {

  			model.addAttribute("picUrl","/img/photo_s.jpg");
		}
		model.addAttribute("username", getUser().getUsername());
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

		password = MD5Utils.encrypt(username, password);
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser.isAuthenticated() && currentUser.isRemembered()) {
			return R.ok();
		} else
		{
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			if (rememberme != null) {
				token.setRememberMe(true);
			}
			try {
				currentUser.login(token);
				return R.ok();
			} catch (AuthenticationException e) {
				return R.error("用户名或密码错误");
			}
		}
	}

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
