package com.justdo.common.controller;

import com.justdo.common.utils.ShiroUtils;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 基础Controller
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */
@Controller
public class BaseController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//true:允许输入空值，false:不能为空值
	}
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
		return getSimpleEmployee().getId();
	}

	/**
	 * 得到当前员工名登录名
	 * @return
	 */
	public String getEmployeename() {
		return getSimpleEmployee().getLoginName();
	}
}