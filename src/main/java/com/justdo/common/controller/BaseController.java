package com.justdo.common.controller;

import com.justdo.common.utils.ShiroUtils;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
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
	public SimpleEmployeeDO getSimpleEmployee() {
		return ShiroUtils.getSimpleEmployee();
	}

	/**
	 * 得到当前员工ID
	 * @return
	 */
	public String getEmployeeId() {
		return getSimpleEmployee().getEmployeeId();
	}

	/**
	 * 得到当前员工名登录名
	 * @return
	 */
	public String getEmployeename() {
		return getSimpleEmployee().getLoginName();
	}
}