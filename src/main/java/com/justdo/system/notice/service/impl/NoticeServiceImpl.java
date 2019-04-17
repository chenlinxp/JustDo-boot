package com.justdo.system.notice.service.impl;

import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.PageUtils;
import com.justdo.system.dict.service.DictContentService;
import com.justdo.system.employee.dao.EmployeeDao;
import com.justdo.system.employee.domain.EmployeeDO;
import com.justdo.system.employee.service.ESessionService;
import com.justdo.system.notice.dao.NoticeDao;
import com.justdo.system.notice.dao.NoticeRecordDao;
import com.justdo.system.notice.domain.NoticeDO;
import com.justdo.system.notice.domain.NoticeDTO;
import com.justdo.system.notice.domain.NoticeRecordDO;
import com.justdo.system.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    private EmployeeDao employeeDao;
    @Autowired
    private DictContentService dictContentService;
    @Autowired
    private ESessionService esessionService;
    @Autowired
    private SimpMessagingTemplate template;

    @Override
    public NoticeDO get(String id) {
        NoticeDO rDO = NoticeDao.get(id);
        rDO.setType(dictContentService.getName("notice_type", rDO.getType()));
        return rDO;
    }

    @Override
    public List<NoticeDO> list(Map<String, Object> map) {
        List<NoticeDO> Notices = NoticeDao.list(map);
        for (NoticeDO NoticeDO : Notices) {
            NoticeDO.setType(dictContentService.getName("notice_type", NoticeDO.getType()));
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
        String[] userIds = Notice.getUserIds();
        String NoticeId = Notice.getId();
        List<NoticeRecordDO> records = new ArrayList<>();
        for (String userId : userIds) {
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
                for (EmployeeDO employeeDO : esessionService.listOnlineEmployee()) {
                    for (String userId : userIds) {
                        if (userId.equals(employeeDO.getEmployeeId())) {
                            template.convertAndSendToUser(userId, "/queue/notifications", "新消息：" + Notice.getTitle());
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
    public int del(String id) {
        recordDao.delByNotifbyId(id);
        return NoticeDao.del(id);
    }

    @Transactional
    @Override
    public int batchDel(String[] ids) {
        recordDao.batchDelByNotifbyId(ids);
        return NoticeDao.batchDel(ids);
    }


    @Override
    public PageUtils selfList(Map<String, Object> map) {
        List<NoticeDTO> rows = NoticeDao.listDTO(map);
        for (NoticeDTO NoticeDTO : rows) {
            NoticeDTO.setBefore(DateUtils.getTimeBefore(NoticeDTO.getUpdateDate()));
            NoticeDTO.setSender(employeeDao.get(NoticeDTO.getCreateBy()).getRealName());
        }
        PageUtils page = new PageUtils(rows, NoticeDao.countDTO(map));
        return page;
    }

}
