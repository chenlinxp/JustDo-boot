package com.justdo.common.controller;

import com.justdo.common.utils.ShiroUtils;
import com.justdo.system.employee.domain.EmployeeDO;
import org.springframework.stereotype.Controller;

/**
 *
 * 基础Controller
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */
@Controller
public class BaseController {

	/**
	 * 获取当前对象
	 * @return EmployeeDO
	 */
	public EmployeeDO getEmployee() {
		return ShiroUtils.getEmployee();
	}

	/**
	 * 得到当前员工ID
	 * @return
	 */
	public String getEmployeeId() {
		return getEmployee().getEmployeeId();
	}

	/**
	 * 得到当前员工名登录名
	 * @return
	 */
	public String getEmployeename() {
		return getEmployee().getLoginName();
	}
}