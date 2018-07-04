package com.justdo.system.notice.dao;

import com.justdo.system.notice.domain.NoticeRecordDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 通知通告发送记录
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-09 17:18:45
 */
@Mapper
public interface NoticeRecordDao {

	NoticeRecordDO get(Long id);

	List<NoticeRecordDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(NoticeRecordDO NoticeRecord);

	int update(NoticeRecordDO NoticeRecord);

	int remove(Long id);

	int batchRemove(Long[] ids);

	int batchSave(List<NoticeRecordDO> records);

	Long[] listNoticeIds(Map<String, Object> map);

	int removeByNotifbyId(Long NoticeId);

	int batchRemoveByNotifbyId(Long[] NoticeIds);

	int changeRead(NoticeRecordDO NoticeRecord);
}
