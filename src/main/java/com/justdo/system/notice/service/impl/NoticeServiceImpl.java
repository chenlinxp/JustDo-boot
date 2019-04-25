package com.justdo.system.notice.service.impl;

import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.PageUtils;
import com.justdo.common.utils.StringUtils;
import com.justdo.system.dict.domain.DictContentDO;
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
    private NoticeDao noticeDao;
    @Autowired
    private NoticeRecordDao noticeRecordDao;
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
        NoticeDO noticeDO = noticeDao.get(id);
//        List<DictContentDO> listD = dictContentService.listDictByCode("noticeCode");
//        noticeDO.setNoticeType(dictContentService.getName(listD.get(0).getDid(), noticeDO.getNoticeType()));
        return noticeDO;
    }

    @Override
    public List<NoticeDO> list(Map<String, Object> map) {
        List<NoticeDO> Notices = noticeDao.list(map);
        List<DictContentDO> listD = dictContentService.listDictByCode("noticeCode");
        for (NoticeDO NoticeDO : Notices) {

            NoticeDO.setNoticeType(dictContentService.getName(listD.get(0).getDid(), NoticeDO.getNoticeType()));
        }
        return Notices;
    }

    @Override
    public int count(Map<String, Object> map) {
        return noticeDao.count(map);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int save(NoticeDO notice) {
        int r = noticeDao.save(notice);
        // 保存到接受者列表中
        String[] employeeIds = notice.getEmployeeIds();
        if(employeeIds.length>0) {
            String NoticeId = notice.getNoticeId();
            List<NoticeRecordDO> records = new ArrayList<>();
            for (String employeeId : employeeIds) {
                NoticeRecordDO record = new NoticeRecordDO();
                record.setNoticeRecordId(StringUtils.getUUID());
                record.setNoticeId(NoticeId);
                record.setEmployeeId(employeeId);
                record.setIsRead(0);
                records.add(record);
            }
            noticeRecordDao.batchSave(records);
            //给在线用户发送通知
            ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    for (EmployeeDO employeeDO : esessionService.listOnlineEmployee()) {
                        for (String employeeId : employeeIds) {
                            if (employeeId.equals(employeeDO.getEmployeeId())) {
                                template.convertAndSendToUser(employeeId, "/queue/notifications", "新消息：" + notice.getNoticeTitle());
                            }
                        }
                    }
                }
            });
            executor.shutdown();
        }
        return r;
    }

    @Override
    public int update(NoticeDO Notice) {
        return noticeDao.update(Notice);
    }

    @Transactional
    @Override
    public int del(String id) {
        noticeRecordDao.delByNoticebyId(id);
        return noticeDao.del(id);
    }

    @Transactional
    @Override
    public int batchDel(String[] ids) {
        noticeRecordDao.batchDelByNoticebyId(ids);
        return noticeDao.batchDel(ids);
    }


    @Override
    public PageUtils selfList(Map<String, Object> map) {
        List<NoticeDTO> rows = noticeDao.listDTO(map);
        for (NoticeDTO NoticeDTO : rows) {
            NoticeDTO.setBefore(DateUtils.getTimeBefore(NoticeDTO.getCreateTime()));
            NoticeDTO.setSender(employeeDao.get(NoticeDTO.getCreateEmployeeId()).getRealName());
        }
        PageUtils page = new PageUtils(rows, noticeDao.countDTO(map));
        return page;
    }

}
