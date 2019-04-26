package com.justdo.system.notice.dao;

import com.justdo.system.notice.domain.NoticeRecordDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 通知通告发送记录
 * 
 * @author chenlin
 * @email
 * @date 2017-10-09 17:18:45
 */
@Mapper
public interface NoticeRecordDao {

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
	 * @param NoticeRecord
	 * @return
	 */
	int save(NoticeRecordDO NoticeRecord);

	/**
	 * 编辑通知通告发送记录
	 * @param NoticeRecord
	 * @return
	 */
	int update(NoticeRecordDO NoticeRecord);

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
	 * 批量保存通知通告发送记录
	 * @param records
	 * @return
	 */
	int batchSave(List<NoticeRecordDO> records);
	/**
	 * 获取通知NoticeIds
	 * @param map
	 * @return
	 */
	String[] listNoticeIds(Map<String, Object> map);

	/**
	 * 通过NoticeId删除通知通告发送记录
	 * @param NoticeId
	 * @return
	 */
	int delByNoticebyId(String NoticeId);

	/**
	 * 通过NoticeIds批量删除通知通告发送记录
	 * @param NoticeIds
	 * @return
	 */
	int batchDelByNoticebyId(String[] NoticeIds);

	/**
	 * 更改阅读状态
	 * @param NoticeRecord
	 * @return
	 */
	int changeRead(NoticeRecordDO NoticeRecord);
}
