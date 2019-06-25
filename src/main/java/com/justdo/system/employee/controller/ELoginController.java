
package com.justdo.system.employee.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.domain.Tree;
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

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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
	@Autowired
	private Producer captchaProducer;


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
	R ajaxLogin(String username, String password, String rememberme, String verificationcode,Model model,HttpSession session) {

		//1、检验验证码
		if (verificationcode != null) {
			String captchaSession = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (!Objects.equals(verificationcode, captchaSession)) {
				//log.info("验证码错误，用户输入：{}, 正确验证码：{}", inputCode, captchaSession);
				//model.addAttribute("msg", "验证码不正确!");
				return R.error("验证码不正确");
			}
		}
		String loginName = username.trim();
		Map<String ,Object> params = new HashMap<>(1);
		params.put("loginName",loginName);
		if(employeeService.exist(params)) {
			String salt = employeeService.getPasswordSalt(loginName);
//			String a = password2.toString();//4d9573ec9f4cf8978543486c9e9eb681
			//password = MD5Utils.encrypt(salt, password);  //fe02dde7415743a285fc5cefa6942ffc
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

	/**
	 * 验证码
	 */
	@ResponseBody
	@GetMapping("/verification")
	public void verification(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setDateHeader("Expires", 0);
	 //Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	// Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
	// return a jpeg
        response.setContentType("image/jpeg");
	// create the text for the image
	String capText = captchaProducer.createText();
	//将验证码存到session
	request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
	// create the image with the text
	BufferedImage bi = captchaProducer.createImage(capText);
	ServletOutputStream out = response.getOutputStream();
	// write the data out
        ImageIO.write(bi, "jpg", out);
        try {
		out.flush();
	} finally {
		out.close();
	}
}


	/**
	 * 短信验证码
	 */
//	@ResponseBody
//	@RequestMapping("/mobile/code/{number}")
//	public Map<String, Object> mobile(@PathVariable("number") String number){
//
//
//		//账号不存在
////		if(employeeDO == null) {
////			return R.error("手机号码未注册");
////		}
//
//		//生成4位验证码
//		String code = "123456";
//		//redis 60秒
//		redisUtils.set(number,code,60);
//		//调用短信服务去发送
//
//		return R.ok("验证码发送成功");
//	}

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
