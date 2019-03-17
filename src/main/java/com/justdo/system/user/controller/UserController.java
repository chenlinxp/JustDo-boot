package com.justdo.system.user.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.controller.BaseController;
import com.justdo.common.domain.Tree;
import com.justdo.common.utils.MD5Utils;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.Query;
import com.justdo.common.utils.R;
import com.justdo.system.dept.domain.DeptDO;
import com.justdo.system.dict.service.DictContentService;
import com.justdo.system.role.domain.RoleDO;
import com.justdo.system.role.service.RoleService;
import com.justdo.system.user.domain.UserDO;
import com.justdo.system.user.domain.UserVO;
import com.justdo.system.user.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@RequestMapping("/system/user")
@Controller
public class UserController extends BaseController {

	private String preUrl="system/user"  ;
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	DictContentService dictContentService;

	/**
	 * 用户列表页面
	 * @return 用户列表页面路径
	 */
	@RequiresPermissions("system:user:user")
	@GetMapping("")
	String user() {
		return preUrl + "/user";
	}

	/**
	 * 用户列表数据
	 * @param params
	 * @return 用户列表数据
	 */
	@Log("用户列表")
	@GetMapping("/list")
	@ResponseBody
	PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		List<UserDO> sysUserList = userService.list(query);
		int total = userService.count(query);
		PageUtils pageUtil = new PageUtils(sysUserList, total);
		return pageUtil;
	}

	/**
	 * 用户详情页面
	 * @param id
	 * @return 用户详情页面路径
	 */
	@GetMapping("/view/{id}")
	@RequiresPermissions("system:user:edit")
	String view(@PathVariable("id") String id,Model model){
		UserDO userDO = userService.get(id);
		model.addAttribute("user", userDO);
		return preUrl+"/view";
	}

	/**
	 * 新增用户页面
	 * @param model
	 * @return 新增用户页面路径
	 */
	@RequiresPermissions("system:user:add")
	@GetMapping("/add")
	String add(Model model) {
		List<RoleDO> roles = roleService.list();
		model.addAttribute("roles", roles);
		return preUrl + "/add";
	}

	/**
	 * 编辑用户页面
	 * @param model
	 * @param id
	 * @return 编辑用户页面路径
	 */
	@RequiresPermissions("system:user:edit")
	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") String id) {
		UserDO userDO = userService.get(id);
		model.addAttribute("user", userDO);
		List<RoleDO> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		return preUrl+"/edit";
	}

	/**
	 * 新增用户
	 * @param user
	 * @return R
	 */
	@Log("保存用户")
	@RequiresPermissions("system:user:add")
	@PostMapping("/save")
	@ResponseBody
	R save(UserDO user) {
		user.setPassword(MD5Utils.encrypt(user.getUsername(), user.getPassword()));
		if (userService.save(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 更新用户
	 * @param user
	 * @return R
	 */
	@Log("更新用户")
	@RequiresPermissions("system:user:edit")
	@PostMapping("/update")
	@ResponseBody
	R update(UserDO user) {
		if (userService.update(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 更新个人信息
	 * @param user
	 * @return R
	 */
	@Log("更新个人信息")
	@RequiresPermissions("system:user:edit")
	@PostMapping("/updatePeronal")
	@ResponseBody
	R updatePeronal(UserDO user) {
		if (userService.updatePersonal(user) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除用户
	 * @param id
	 * @return R
	 */
	@Log("删除用户")
	@RequiresPermissions("system:user:del")
	@PostMapping("/del")
	@ResponseBody
	R remove(String id) {
		if (userService.del(id) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 批量删除用户
	 * @param userIds
	 * @return R
	 */
	@Log("批量删除用户")
	@RequiresPermissions("system:user:batchDel")
	@PostMapping("/batchDel")
	@ResponseBody
	R batchDel(@RequestParam("ids[]") String[] userIds) {

		int r = userService.batchDel(userIds);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 退出账号
	 * @param params
	 * @return
	 */
	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !userService.exit(params);
	}

	/**
	 * 更改用户密码页面
	 * @param userId
	 * @param model
	 * @return 更改用户密码页面路径
	 */
	@RequiresPermissions("system:user:resetPwd")
	@GetMapping("/resetPwd/{id}")
	String resetPwd(@PathVariable("id") String userId, Model model) {

		UserDO userDO = new UserDO();
		userDO.setUserId(userId);
		model.addAttribute("user", userDO);
		return preUrl + "/reset_pwd";
	}

	/**
	 * 更改用户密码
	 * @param userVO
	 * @return R
	 */
	@Log("更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(UserVO userVO) {
		try{
			userService.resetPwd(userVO,getUser());
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}

	/**
	 * admin更改用户密码
	 * @param userVO
	 * @return
	 */
	@Log("admin修改用户密码")
	@RequiresPermissions("system:user:resetPwd")
	@PostMapping("/adminResetPwd")
	@ResponseBody
	R adminResetPwd(UserVO userVO) {
		try{
			userService.adminResetPwd(userVO);
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}


	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = userService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  preUrl + "/userTree";
	}

	@GetMapping("/personal")
	String personal(Model model) {
		UserDO userDO  = userService.get(getUserId());
		model.addAttribute("user",userDO);
//		model.addAttribute("hobbyList",dictContentService.getHobbyList(userDO));
//		model.addAttribute("sexList",dictContentService.getSexList());
		return preUrl + "/personal";
	}

	@ResponseBody
	@PostMapping("/uploadImg")
	R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<>();
		try {
			result = userService.updatePersonalImg(file, avatar_data, getUserId());
		} catch (Exception e) {
			return R.error("更新图像失败！");
		}
		if(result!=null && result.size()>0){
			return R.ok(result);
		}else {
			return R.error("更新图像失败！");
		}
	}
}
