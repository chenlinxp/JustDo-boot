package com.justdo.system.notice.service;

import com.justdo.common.utils.PageUtils;
import com.justdo.system.notice.domain.NoticeDO;

import java.util.List;
import java.util.Map;


/**
 * 通知通告发送
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */
public interface NoticeService {

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
	 * @param id
	 * @return
	 */
	int del(String id);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int batchDel(String[] ids);

	/**
	 * 个人收到的消息list
	 * @param map
	 * @return
	 */
	PageUtils selfList(Map<String, Object> map);

	/**
	 * 发送消息
	 */
	void sendMessge(NoticeDO notice);
}
