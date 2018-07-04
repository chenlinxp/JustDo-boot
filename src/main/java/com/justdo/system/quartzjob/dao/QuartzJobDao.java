package com.justdo.system.quartzjob.dao;



import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.justdo.system.quartzjob.domain.QuartzJobTaskDO;

/**
 *
 * 自动任务
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Mapper
public interface QuartzJobDao {

	QuartzJobTaskDO get(Long id);
	
	List<QuartzJobTaskDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(QuartzJobTaskDO task);
	
	int update(QuartzJobTaskDO task);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
