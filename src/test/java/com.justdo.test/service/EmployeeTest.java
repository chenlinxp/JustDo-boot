package com.justdo.test.service;

import com.justdo.system.employee.domain.EmployeeDO;
import com.justdo.system.employee.service.EmployeeService;
import com.justdo.test.JustdoApplicationTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 功能描述 员工Service测试
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/23 上午11:37
 */
public class EmployeeTest extends JustdoApplicationTest {

	@Autowired
	private EmployeeService employeeService;

	@Test
	public void testGetList(){

		List<EmployeeDO>  employeeDOList = employeeService.list(null);

		System.out.println(employeeDOList);

	}

	//忽略方法注解
	@Ignore("not ready yet")
	@Test
	public void testGetEmployee(){

		EmployeeDO  employeeDO = employeeService.get("");

		System.out.println(employeeDO);

	}

}
