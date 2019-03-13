package com.justdo.common.startrunner;

import com.justdo.common.quartz.QuartzManager;
import com.justdo.system.quartzjob.service.QuartzJobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 *
 * 项目启动时进入监听器初始化定时任务。
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */
@Component
@Order(value = 1)
public class ScheduleJobStartRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(ScheduleJobStartRunner.class);

	@Autowired
	QuartzJobService quartzJobService;

	@Autowired
	QuartzManager quartzManager;

	@Override
	public void run(String... arg0) throws Exception {
		try {
			quartzJobService.initSchedule();
			logger.info("CommandLineRunner : ScheduleJobStartupRunner is running,order is 1");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}