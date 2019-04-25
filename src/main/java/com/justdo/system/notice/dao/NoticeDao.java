package com.justdo.system.notice.dao;

import com.justdo.system.notice.domain.NoticeDO;
import com.justdo.system.notice.domain.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 通知通告
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */
@Mapper
public interface NoticeDao {

	NoticeDO get(String id);

	List<NoticeDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(NoticeDO notice);

	int update(NoticeDO notice);

	int del(String noticeId);

	int batchDel(String[] noticeIds);

	List<NoticeDO> listByIds(String[] noticeIds);

	int countDTO(Map<String, Object> map);

	List<NoticeDTO> listDTO(Map<String, Object> map);
}
