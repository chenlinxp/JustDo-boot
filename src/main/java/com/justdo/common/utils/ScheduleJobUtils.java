package com.justdo.common.utils;

import com.justdo.system.quartzjob.domain.QuartzJobDO;
import com.justdo.system.quartzjob.domain.QuartzJobTaskDO;

public class ScheduleJobUtils {
	public static QuartzJobDO entityToData(QuartzJobTaskDO scheduleJobEntity) {
		QuartzJobDO scheduleJob = new QuartzJobDO();
		scheduleJob.setBeanClass(scheduleJobEntity.getBeanClass());
		scheduleJob.setCronExpression(scheduleJobEntity.getCronExpression());
		scheduleJob.setDescription(scheduleJobEntity.getDescription());
		scheduleJob.setIsConcurrent(scheduleJobEntity.getIsConcurrent());
		scheduleJob.setJobName(scheduleJobEntity.getJobName());
		scheduleJob.setJobGroup(scheduleJobEntity.getJobGroup());
		scheduleJob.setJobStatus(scheduleJobEntity.getJobStatus());
		scheduleJob.setMethodName(scheduleJobEntity.getMethodName());
		scheduleJob.setSpringBean(scheduleJobEntity.getSpringBean());
		return scheduleJob;
	}
}