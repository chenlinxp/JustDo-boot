package com.justdo.system.quartzjob.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.justdo.config.ConstantConfig;
import com.justdo.system.quartzjob.dao.QuartzJobDao;
import com.justdo.system.quartzjob.domain.QuartzJobDO;
import com.justdo.system.quartzjob.domain.QuartzJobTaskDO;
import com.justdo.common.quartz.QuartzManager;
import com.justdo.system.quartzjob.service.QuartzJobService;
import com.justdo.common.utils.ScheduleJobUtils;

/**
 *
 * 自动任务
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */
@Service
public class QuartzJobServiceImpl implements QuartzJobService {

	@Autowired
	private QuartzJobDao taskScheduleJobMapper;

	@Autowired
	QuartzManager quartzManager;

	@Override
	public QuartzJobTaskDO get(Long id) {
		return taskScheduleJobMapper.get(id);
	}

	@Override
	public List<QuartzJobTaskDO> list(Map<String, Object> map) {
		return taskScheduleJobMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return taskScheduleJobMapper.count(map);
	}

	@Override
	public int save(QuartzJobTaskDO taskScheduleJob) {
		return taskScheduleJobMapper.save(taskScheduleJob);
	}

	@Override
	public int update(QuartzJobTaskDO taskScheduleJob) {
		return taskScheduleJobMapper.update(taskScheduleJob);
	}

	@Override
	public int remove(Long id) {
		try {
			QuartzJobTaskDO scheduleJob = get(id);
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
			return taskScheduleJobMapper.remove(id);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public int batchDel(Long[] ids) {
		for (Long id : ids) {
			try {
				QuartzJobTaskDO scheduleJob = get(id);
				quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
			} catch (SchedulerException e) {
				e.printStackTrace();
				return 0;
			}
		}
		return taskScheduleJobMapper.batchDel(ids);
	}

	@Override
	public void initSchedule() throws SchedulerException {
		// 这里获取任务信息数据
		List<QuartzJobTaskDO> jobList = taskScheduleJobMapper.list(new HashMap<String, Object>(16));
		for (QuartzJobTaskDO scheduleJob : jobList) {
			if ("1".equals(scheduleJob.getJobStatus())) {
				QuartzJobDO job = ScheduleJobUtils.entityToData(scheduleJob);
				quartzManager.addJob(job);
			}

		}
	}

	@Override
	public void changeStatus(Long jobId, String cmd) throws SchedulerException {
		QuartzJobTaskDO scheduleJob = get(jobId);
		if (scheduleJob == null) {
			return;
		}
		if (ConstantConfig.STATUS_RUNNING_STOP.equals(cmd)) {
			quartzManager.deleteJob(ScheduleJobUtils.entityToData(scheduleJob));
			scheduleJob.setJobStatus(QuartzJobDO.STATUS_NOT_RUNNING);
		} else {
			if (!ConstantConfig.STATUS_RUNNING_START.equals(cmd)) {
			} else {
                scheduleJob.setJobStatus(QuartzJobDO.STATUS_RUNNING);
                quartzManager.addJob(ScheduleJobUtils.entityToData(scheduleJob));
            }
		}
		update(scheduleJob);
	}

	@Override
	public void updateCron(Long jobId) throws SchedulerException {
		QuartzJobTaskDO scheduleJob = get(jobId);
		if (scheduleJob == null) {
			return;
		}
		if (QuartzJobDO.STATUS_RUNNING.equals(scheduleJob.getJobStatus())) {
			quartzManager.updateJobCron(ScheduleJobUtils.entityToData(scheduleJob));
		}
		update(scheduleJob);
	}

}
