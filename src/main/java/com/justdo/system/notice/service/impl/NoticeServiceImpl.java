package com.justdo.system.notice.service.impl;

import java.util.concurrent.TimeUnit;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.justdo.system.user.domain.UserDO;
import com.justdo.system.user.service.SessionService;
import com.justdo.system.dict.service.DictService;
import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.PageUtils;
import com.justdo.system.notice.dao.NoticeDao;
import com.justdo.system.notice.dao.NoticeRecordDao;
import com.justdo.system.notice.domain.NoticeDO;
import com.justdo.system.notice.domain.NoticeDTO;
import com.justdo.system.notice.domain.NoticeRecordDO;
import com.justdo.system.notice.service.NoticeService;
import com.justdo.system.user.dao.UserDao;

/**
 * 通知通告
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeDao NoticeDao;
    @Autowired
    private NoticeRecordDao recordDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DictService dictService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public NoticeDO get(Long id) {
        NoticeDO rDO = NoticeDao.get(id);
        rDO.setType(dictService.getName("notice_type", rDO.getType()));
        return rDO;
    }

    @Override
    public List<NoticeDO> list(Map<String, Object> map) {
        List<NoticeDO> Notices = NoticeDao.list(map);
        for (NoticeDO NoticeDO : Notices) {
            NoticeDO.setType(dictService.getName("notice_type", NoticeDO.getType()));
        }
        return Notices;
    }

    @Override
    public int count(Map<String, Object> map) {
        return NoticeDao.count(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int save(NoticeDO Notice) {
        Notice.setUpdateDate(new Date());
        int r = NoticeDao.save(Notice);
        // 保存到接受者列表中
        Long[] userIds = Notice.getUserIds();
        Long NoticeId = Notice.getId();
        List<NoticeRecordDO> records = new ArrayList<>();
        for (Long userId : userIds) {
            NoticeRecordDO record = new NoticeRecordDO();
            record.setNoticeId(NoticeId);
            record.setUserId(userId);
            record.setIsRead(0);
            records.add(record);
        }
        recordDao.batchSave(records);
        //给在线用户发送通知
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,0, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<>());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (UserDO userDO : sessionService.listOnlineUser()) {
                    for (Long userId : userIds) {
                        if (userId.equals(userDO.getUserId())) {
                            template.convertAndSendToUser(userDO.toString(), "/queue/notifications", "新消息：" + Notice.getTitle());
                        }
                    }
                }
            }
        });
        executor.shutdown();
        return r;
    }

    @Override
    public int update(NoticeDO Notice) {
        return NoticeDao.update(Notice);
    }

    @Transactional
    @Override
    public int remove(Long id) {
        recordDao.removeByNotifbyId(id);
        return NoticeDao.remove(id);
    }

    @Transactional
    @Override
    public int batchRemove(Long[] ids) {
        recordDao.batchRemoveByNotifbyId(ids);
        return NoticeDao.batchRemove(ids);
    }


    @Override
    public PageUtils selfList(Map<String, Object> map) {
        List<NoticeDTO> rows = NoticeDao.listDTO(map);
        for (NoticeDTO NoticeDTO : rows) {
            NoticeDTO.setBefore(DateUtils.getTimeBefore(NoticeDTO.getUpdateDate()));
            NoticeDTO.setSender(userDao.get(NoticeDTO.getCreateBy()).getName());
        }
        PageUtils page = new PageUtils(rows, NoticeDao.countDTO(map));
        return page;
    }

}
