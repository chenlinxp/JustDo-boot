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

	NoticeDO get(String id);

	List<NoticeDO> list(Map<String, Object> map);

	int count(Map<String, Object> map);

	int save(NoticeDO notice);

	int update(NoticeDO notice);

	int del(String id);

	int batchDel(String[] ids);

	PageUtils selfList(Map<String, Object> map);
}
