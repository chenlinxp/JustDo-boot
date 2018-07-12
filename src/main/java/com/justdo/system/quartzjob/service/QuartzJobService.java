package com.justdo.system.quartzjob.service;


import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;
import com.justdo.system.quartzjob.domain.QuartzJobTaskDO;


/**
 *
 * 自动任务
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */

public interface QuartzJobService {

	QuartzJobTaskDO get(Long id);
	
	List<QuartzJobTaskDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(QuartzJobTaskDO taskScheduleJob);
	
	int update(QuartzJobTaskDO taskScheduleJob);
	
	int remove(Long id);
	
	int batchDel(Long[] ids);

	void initSchedule() throws SchedulerException;

	void changeStatus(Long jobId, String cmd) throws SchedulerException;

	void updateCron(Long jobId) throws SchedulerException;
}
