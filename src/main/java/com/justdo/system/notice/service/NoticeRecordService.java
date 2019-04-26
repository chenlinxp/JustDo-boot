package com.justdo.system.notice.service;

import com.justdo.system.notice.domain.NoticeRecordDO;

import java.util.List;
import java.util.Map;


/**
 * 通知通告发送记录
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */
public interface NoticeRecordService {

	/**
	 * 获取通知通告发送记录实体
	 * @param id
	 * @return
	 */
	NoticeRecordDO get(String id);

	/**
	 * 获取通知通告发送记录实体list
	 * @param map
	 * @return
	 */
	List<NoticeRecordDO> list(Map<String, Object> map);

	/**
	 * 获取通知通告发送记录数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存通知通告发送记录
	 * @param notifyRecord
	 * @return
	 */
	int save(NoticeRecordDO notifyRecord);

	/**
	 * 编辑通知通告发送记录
	 * @param notifyRecord
	 * @return
	 */
	int update(NoticeRecordDO notifyRecord);

	/**
	 * 删除通知通告发送记录
	 * @param id
	 * @return
	 */
	int del(String id);

	/**
	 * 批量删除通知通告发送记录
	 * @param ids
	 * @return
	 */
	int batchDel(String[] ids);

	/**
	 * 更改阅读状态
	 * @return
	 */
	int changeRead(NoticeRecordDO noticeRecord);
}
