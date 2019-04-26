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

	/**
	 * 获取通知实体
	 * @param id
	 * @return
	 */
	NoticeDO get(String id);

	/**
	 * 获取通知实体list
	 * @param map
	 * @return
	 */
	List<NoticeDO> list(Map<String, Object> map);

	/**
	 * 获取通知实体数量
	 * @param map
	 * @return
	 */
	int count(Map<String, Object> map);

	/**
	 * 保存通知实体
	 * @param notice
	 * @return
	 */
	int save(NoticeDO notice);

	/**
	 * 编辑通知
	 * @param notice
	 * @return
	 */
	int update(NoticeDO notice);

	/**
	 * 删除
	 * @param noticeId
	 * @return
	 */
	int del(String noticeId);

	/**
	 * 批量删除
	 * @param noticeIds
	 * @return
	 */
	int batchDel(String[] noticeIds);

	/**
	 * 通过noticeIds获取通知实体list
	 * @param noticeIds
	 * @return
	 */
	List<NoticeDO> listByIds(String[] noticeIds);

	/**
	 * 获取发送通知通告实体数量
	 * @param map
	 * @return
	 */
	int countDTO(Map<String, Object> map);

	/**
	 * 获取发送通知通告实体list
	 * @param map
	 * @return
	 */
	List<NoticeDTO> listDTO(Map<String, Object> map);
}
