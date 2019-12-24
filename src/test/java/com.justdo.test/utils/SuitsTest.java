package com.justdo.test.utils;

import com.justdo.test.service.EmployeeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 功能描述：打包测试
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/24 下午6:03
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({EmployeeTest.class,FilterTest.class})
public class SuitsTest {

	//不需要写代码只需要注解
}
