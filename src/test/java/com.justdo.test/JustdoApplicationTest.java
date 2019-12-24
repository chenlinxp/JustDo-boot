
package com.justdo.test;

import com.justdo.JustdoApplication2;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={JustdoApplication2.class})//启动整个springboot工程
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class JustdoApplicationTest {

//	@BeforeClass
//	public static void init() {
//		System.out.println("初始化测试-----------------");
//	}

	@Before
	public void begin() {
		System.out.println("开始测试-----------------");
	}

	@After
	public void end() {
		System.out.println("测试结束-----------------");
	}

//	@AfterClass
//	public void endHandle() {
//		System.out.println("测试结束处理-----------------");
//	}
}