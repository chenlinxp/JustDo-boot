package com.justdo.system.notice.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.justdo.system.notice.dao.NoticeRecordDao;
import com.justdo.system.notice.domain.NoticeRecordDO;
import com.justdo.system.notice.service.NoticeRecordService;


/**
 * 通知通告
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */
@Service
public class NoticeRecordServiceImpl implements NoticeRecordService {
	@Autowired
	private NoticeRecordDao NoticeRecordDao;
	
	@Override
	public NoticeRecordDO get(Long id){
		return NoticeRecordDao.get(id);
	}
	
	@Override
	public List<NoticeRecordDO> list(Map<String, Object> map){
		return NoticeRecordDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return NoticeRecordDao.count(map);
	}
	
	@Override
	public int save(NoticeRecordDO NoticeRecord){
		return NoticeRecordDao.save(NoticeRecord);
	}
	
	@Override
	public int update(NoticeRecordDO NoticeRecord){
		return NoticeRecordDao.update(NoticeRecord);
	}
	
	@Override
	public int remove(Long id){
		return NoticeRecordDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return NoticeRecordDao.batchRemove(ids);
	}

	@Override
	public int changeRead(NoticeRecordDO NoticeRecord) {
		return NoticeRecordDao.changeRead(NoticeRecord);
	}

}
