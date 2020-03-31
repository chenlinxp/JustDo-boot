package com.justdo.test.service;

import com.justdo.system.employee.domain.EmployeeDO;
import com.justdo.system.employee.service.EmployeeService;
import com.justdo.test.JustdoApplicationTest;
import org.junit.Assert;
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

		//第一个参数：如果测试不通过，会抛出此消息，此参数可不要；

		//第二个参数：我预期的值，我这里希望他查出来的结果是600；

		//第三个参数：是实际的结果，就是我们调用方法返回的结果；
		Assert.assertSame("员工数据列表有误",11,employeeService.list(null).size());

	}

	//忽略方法注解
	@Ignore("not ready yet")
	@Test
	public void testGetEmployee(){

		EmployeeDO  employeeDO = employeeService.get("");

		System.out.println(employeeDO);

	}

}
