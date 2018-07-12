package com.justdo.system.notice.service;

import java.util.List;
import java.util.Map;
import com.justdo.system.notice.domain.NoticeRecordDO;



/**
 * 通知通告发送记录
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */
public interface NoticeRecordService {
	
	NoticeRecordDO get(Long id);
	
	List<NoticeRecordDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(NoticeRecordDO notifyRecord);
	
	int update(NoticeRecordDO notifyRecord);
	
	int remove(Long id);
	
	int batchDel(Long[] ids);

	/**
	 * 更改阅读状态
	 * @return
	 */
	int changeRead(NoticeRecordDO notifyRecord);
}
