package com.justdo.common.listenner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.justdo.common.quartz.utils.QuartzManager;
import com.justdo.system.quartzjob.service.QuartzJobService;

@Component
@Order(value = 1)
public class ScheduleJobInitListener implements CommandLineRunner {

	@Autowired
	QuartzJobService quartzJobService;

	@Autowired
	QuartzManager quartzManager;

	@Override
	public void run(String... arg0) throws Exception {
		try {
			quartzJobService.initSchedule();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}