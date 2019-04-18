package com.justdo.system.employee.controller;


import com.justdo.common.annotation.Log;
import com.justdo.common.domain.Tree;
import com.justdo.common.utils.*;
import com.justdo.system.dept.domain.DeptDO;
import com.justdo.system.dict.service.DictContentService;
import com.justdo.system.employee.domain.EmployeeDO;
import com.justdo.system.employee.domain.EmployeeVO;
import com.justdo.system.employee.service.EmployeeService;
import com.justdo.system.role.domain.RoleDO;
import com.justdo.system.role.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.justdo.common.utils.ShiroUtils.getEmployee;
import static com.justdo.common.utils.ShiroUtils.getEmployeeId;

/**
 * 员工管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:19:10
 */
 
@Controller
@RequestMapping("/system/employee")
public class EmployeeController {

	private String preUrl="system/employee"  ;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	RoleService roleService;
	@Autowired
	DictContentService dictContentService;



	/**
	* 列表页面
	* @return 列表页面路径
	*/
	@GetMapping()
	@RequiresPermissions("system:employee:list")
	String Employee(){
	    return preUrl + "/employee";
	}


	/**
	* 列表数据
	* @param params
	* @return
	*/
	@Log("员工管理列表")
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:employee:list")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<EmployeeDO> employeeList = employeeService.list(query);
		int total = employeeService.count(query);
		PageUtils pageUtils = new PageUtils(employeeList, total);
		return pageUtils;
	}

	/**
	* 详情页面
	* @param employeeId
	* @return 详情页面路径
	*/
	@GetMapping("/view/{employeeId}")
	@RequiresPermissions("system:employee:view")
	String view(@PathVariable("employeeId") String employeeId,Model model){
			EmployeeDO employee = employeeService.get(employeeId);
		model.addAttribute("employee", employee);
		return preUrl + "/view";
	}

	/**
	* 增加页面
	* @return 增加页面路径
	*/
	@GetMapping("/add")
	@RequiresPermissions("system:employee:add")
		String add(Model model) {
			List<RoleDO> roles = roleService.list();
			model.addAttribute("roles", roles);
			return preUrl + "/add";
	}


	/**
	 * 保存
	 * @param employee
     * @return R
	 */
	@Log("员工管理保存")
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("system:employee:add")
	public R save( EmployeeDO employee){
		Date date = new Date();
		employee.setCreateTime(date);
		employee.setModifyTime(date);
		employee.setPasswordSalt(StringUtils.getUUID());
		employee.setPassword(MD5Utils.encrypt(employee.getPasswordSalt(), employee.getPassword()));
		if(employeeService.save(employee)>0){
			return R.ok();
		}
		return R.error("保存成功");
	}

	/**
	* 编辑页面
    * @param employeeId
    * @return 编辑页面路径
    */
	@GetMapping("/edit/{employeeId}")
	@RequiresPermissions("system:employee:edit")
	String edit(@PathVariable("employeeId") String employeeId,Model model){
		EmployeeDO employee = employeeService.get(employeeId);
		model.addAttribute("employee", employee);
		List<RoleDO> roles = roleService.list(employeeId);
		model.addAttribute("roles", roles);
	    return preUrl + "/edit";
	}
	

	/**
	 * 更新
	 * @param employee
	 * @return R
	 */
	@Log("员工管理更新")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("system:employee:edit")
	public R update( EmployeeDO employee){
		if(employeeService.update(employee)>0){
			return R.ok();
		}
		return R.error("更新成功");
	}
	
	/**
	 * 删除
	 * @param employeeId
	 * @return R
	 */
	@Log("员工管理删除")
	@PostMapping( "/del")
	@ResponseBody
	@RequiresPermissions("system:employee:del")
	public R remove( String employeeId){
		if(employeeService.del(employeeId)>0){
		return R.ok();
		}
		return R.error("删除成功");
	}
	
	/**
	 * 批量删除
	 * @param employeeIds
	 * @return R
	 */
	@Log("员工管理批量删除")
	@PostMapping( "/batchDel")
	@ResponseBody
	@RequiresPermissions("system:employee:batchDel")
	public R remove(@RequestParam("ids[]") String[] employeeIds){
		if(employeeService.batchDel(employeeIds)>0){
			return R.ok();
		}
		return R.error("批量删除成功");
	}


	@RequiresPermissions("system:employee:edit")
	@Log("更新员工信息")
	@PostMapping("/updatePeronal")
	@ResponseBody
	R updatePeronal(EmployeeDO employeeDO) {

		if (employeeService.updatePersonal(employeeDO) > 0) {
			return R.ok();
		}
		return R.error();
	}

	@PostMapping("/exit")
	@ResponseBody
	boolean exit(@RequestParam Map<String, Object> params) {
		// 存在，不通过，false
		return !employeeService.exit(params);
	}

	@RequiresPermissions("system:employee:resetPwd")
	@Log("请求更改用户密码")
	@GetMapping("/resetPwd/{employeeId}")
	String resetPwd(@PathVariable("employeeId") String employeeId, Model model) {

		EmployeeDO employeeDO = new EmployeeDO();
		employeeDO.setEmployeeId(employeeId);
		model.addAttribute("employee", employeeDO);
		return preUrl + "/reset_pwd";
	}

	@Log("提交更改用户密码")
	@PostMapping("/resetPwd")
	@ResponseBody
	R resetPwd(EmployeeVO employeeVO) {
		try{
			employeeService.resetPwd(employeeVO,getEmployee());
			return R.ok();
		}catch (Exception e){
			return R.error(1,e.getMessage());
		}

	}
	@RequiresPermissions("system:employee:resetPwd")
	@Log("admin提交更改用户密码")
	@PostMapping("/adminResetPwd")
	@ResponseBody
	R adminResetPwd(EmployeeVO employeeVO) {
		try{
			employeeService.adminResetPwd(employeeVO);
			return R.ok();
		}catch (Exception e){
			System.out.println(e.getMessage());
			return R.error(1,e.getMessage());
		}

	}
	@GetMapping("/tree")
	@ResponseBody
	public Tree<DeptDO> tree() {
		Tree<DeptDO> tree = new Tree<DeptDO>();
		tree = employeeService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  preUrl + "/employeeTree";
	}

	@GetMapping("/personal")
	String personal(Model model) {
		EmployeeDO employeeDO  = employeeService.get(getEmployeeId());
		model.addAttribute("employee",employeeDO);
		model.addAttribute("hobbyList",dictContentService.listDictByCode("hobbyCode"));
		model.addAttribute("sexList",dictContentService.listDictByCode("sexCode"));
		return preUrl + "/personal";
	}
	@ResponseBody
	@PostMapping("/uploadImg")
	R uploadImg(@RequestParam("avatar_file") MultipartFile file, String avatar_data, HttpServletRequest request) {

		Map<String, Object> result = new HashMap<>();
		try {
			result = employeeService.updatePersonalImg(file, avatar_data, getEmployeeId());
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
