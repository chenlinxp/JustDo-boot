package com.justdo.system.quartzjob.service;


import com.justdo.system.quartzjob.domain.QuartzJobTaskDO;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Map;


/**
 *
 * 自动任务
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */

public interface QuartzJobService {



	/**
	 * 返回实体
	 * @param schedulerTaskId
	 * @return SchedulerTaskDO
	 */
	QuartzJobTaskDO get(String schedulerTaskId);

	/**
	 * 返回实体list
	 * @param map
	 * @return list
	 */
	List<QuartzJobTaskDO> list(Map<String,Object> map);

	/**
	 * 返回数量
	 * @param map
	 * @return
	 */
	int count(Map<String,Object> map);

	/**
	 * 保存实体
	 * @param schedulerTask
	 * @return
	 */
	int save(QuartzJobTaskDO schedulerTask);

	/**
	 * 更新实体
	 * @param schedulerTask
	 * @return list
	 */
	int update(QuartzJobTaskDO schedulerTask);

	/**
	 * 删除实体
	 * @param schedulerTaskId
	 * @return list
	 */
	int del(String schedulerTaskId);

	/**
	 * 批量删除实体
	 * @param schedulerTaskIds
	 * @return list
	 */
	int batchDel(String[] schedulerTaskIds);


	/**
	 * 初始化计划任务
	 * @throws SchedulerException
	 */
	void initSchedule() throws SchedulerException;

	/**
	 * 改变任务状态
	 * @param schedulerTaskId
	 * @param cmd
	 * @throws SchedulerException
	 */
	void changeStatus(String schedulerTaskId, String cmd) throws SchedulerException;

	/**
	 * 更新任务Cron
	 * @param schedulerTaskId
	 * @throws SchedulerException
	 */
	void updateCron(String schedulerTaskId) throws SchedulerException;
}
