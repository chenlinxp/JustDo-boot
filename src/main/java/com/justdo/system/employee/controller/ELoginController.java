
package com.justdo.system.employee.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.domain.Tree;
import com.justdo.common.utils.*;
import com.justdo.config.ShiroSessionListener;
import com.justdo.system.employee.service.EmployeeService;
import com.justdo.system.file.service.FileService;
import com.justdo.system.resource.domain.ResourceDO;
import com.justdo.system.resource.service.ResourceService;
import com.justdo.system.role.domain.RoleDO;
import com.justdo.system.role.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	private static Logger logger = LoggerFactory.getLogger(ELoginController.class);

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

	@Autowired
	RedisUtils redisUtils;


	@GetMapping({ "/", "" })
	String welcome(Model model) {
		model.addAttribute("title", "登录");
		model.addAttribute("keywords", "JutsDo");
		model.addAttribute("description", "JutsDo登录");
		return "redirect:/justdo/login";
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

	@GetMapping("/justdo/login")
	String login(Model model) {
		model.addAttribute("title", "登录");
		model.addAttribute("keywords", "JutsDo");
		model.addAttribute("description", "JutsDo登录");
		return "login";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password, String rememberMe, String verificationcode,Model model,HttpSession session) {

		//1、检验验证码
		if (verificationcode != null) {
			String captchaSession = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (!Objects.equals(verificationcode, captchaSession)) {
				//log.info("验证码错误，用户输入：{}, 正确验证码：{}", inputCode, captchaSession);
				//model.addAttribute("msg", "验证码不正确!");
				return R.error("验证码不正确");
			}
		}
		username = username.trim();
		Map<String ,Object> params = new HashMap<>(1);
		params.put("loginName",username);
		int logincount = 0 ;
		if(employeeService.exist(params)) {
			String salt = employeeService.getPasswordSalt(username);
//			String a = password2.toString();//4d9573ec9f4cf8978543486c9e9eb681
			//password = MD5Utils.encrypt(salt, password);  //fe02dde7415743a285fc5cefa6942ffc
			Subject currentUser = SecurityUtils.getSubject();
			if (currentUser.isAuthenticated() && currentUser.isRemembered()) {

				logincount = ApplicationContextUtils.getBean(ShiroSessionListener.class).getSessionCount().intValue();

				logger.info("当前在线的用户数是："+ logincount);

				return R.ok();
			} else {
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				String msg = "系统服务异常，请稍后再试！";
				if (rememberMe != null) {
					token.setRememberMe(true);
				}
				try {
					currentUser.login(token);
					logincount = ApplicationContextUtils.getBean(ShiroSessionListener.class).getSessionCount().intValue();

					logger.info("当前在线的用户数是："+ logincount);
					return R.ok();
			    } catch (IncorrectCredentialsException e) {
					msg = "登录密码错误";
					System.out.println("登录密码错误!!!" + e);
			    } catch (ExcessiveAttemptsException e) {
					msg = "登录失败次数过多，请5分钟后再登录!";
					System.out.println("登录失败次数过多!!!" + e);
				} catch (LockedAccountException e) {
					msg = "帐号已被锁定";
					System.out.println("帐号已被锁定!!!" + e);
			    } catch (DisabledAccountException e) {
					msg = "帐号已被禁用";
					System.out.println("帐号已被禁用!!!" + e);
			    } catch (ExpiredCredentialsException e) {
					msg = "帐号已过期";
					System.out.println("帐号已过期!!!" + e);
			    } catch (UnknownAccountException e) {
					msg = "帐号不存在";
					System.out.println("帐号不存在!!!" + e);
			    } catch (UnauthorizedException e) {
					msg = "您没有得到相应的授权！";
					System.out.println(e.toString());
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				return R.error(msg);
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
	@ResponseBody
	@GetMapping("/mobile/code/{number}")
	public Map<String, Object> mobile(@PathVariable("number") String number){


		//账号不存在
//		if(employeeDO == null) {
//			return R.error("手机号码未注册");
//		}
		//生成4位验证码
		String code = "123456";
		//redis 60秒
		byte[] numberByte = StringUtils.getByteString(number);
		byte[] codeByte = StringUtils.getByteString(code);
		redisUtils.set(numberByte, codeByte,60);

		//调用短信服务去发送

		return R.ok("验证码发送成功");
	}

	@Log("退出")
	@GetMapping("/logout")
	String logout() {

		ShiroUtils.logout();

		int  logincount = 0;

		logincount = ApplicationContextUtils.getBean(ShiroSessionListener.class).getSessionCount().intValue();

		logger.info("当前在线的用户数是："+ logincount);

		return "redirect:/justdo/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

}
