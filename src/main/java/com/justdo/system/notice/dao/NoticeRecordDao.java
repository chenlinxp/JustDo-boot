package com.justdo.system.notice.dao;

import com.justdo.system.notice.domain.NoticeRecordDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 通知通告发送记录
 * 
 * @author chenlin
 * @email
 * @date 2017-10-09 17:18:45
 */
@Mapper
public interface NoticeRecordDao {

	NoticeRecordDO get(String id);

	List<NoticeRecordDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(NoticeRecordDO NoticeRecord);

	int update(NoticeRecordDO NoticeRecord);

	int del(String id);

	int batchDel(String[] ids);

	int batchSave(List<NoticeRecordDO> records);

	String[] listNoticeIds(Map<String, Object> map);

	int delByNotifbyId(String NoticeId);

	int batchDelByNotifbyId(String[] NoticeIds);

	int changeRead(NoticeRecordDO NoticeRecord);
}
