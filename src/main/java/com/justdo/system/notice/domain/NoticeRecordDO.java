package com.justdo.system.notice.domain;

import java.io.Serializable;
import java.util.Date;


/**
 * 通知通告发送记录
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */
public class NoticeRecordDO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *  编号
     */
    private Long id;
    //通知通告ID
    private Long noticeId;
    //接受人
    private Long userId;
    //阅读标记
    private Integer isRead;
    //阅读时间
    private Date readDate;

    /**
     * 设置：编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取：编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置：通知通告ID
     */
    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    /**
     * 获取：通知通告ID
     */
    public Long getNoticeId() {
        return noticeId;
    }

    /**
     * 设置：接受人
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：接受人
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：阅读标记
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    /**
     * 获取：阅读标记
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * 设置：阅读时间
     */
    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    /**
     * 获取：阅读时间
     */
    public Date getReadDate() {
        return readDate;
    }

    @Override
    public String toString() {
        return "noticeRecordDO{" +
                "id=" + id +
                ", noticeId=" + noticeId +
                ", userId=" + userId +
                ", isRead=" + isRead +
                ", readDate=" + readDate +
                '}';
    }
}
