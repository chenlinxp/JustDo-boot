package com.justdo.system.quartzjob.dao;



import com.justdo.system.quartzjob.domain.QuartzJobTaskDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 *
 * 自动任务
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface QuartzJobDao {


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
}
