package com.justdo.system.notice.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * 通知通告发送记录
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-04-25 12:46:32
 */
public class NoticeRecordDO extends BaseBean {


    //主键ID
    private String noticeRecordId;

    //通知通告ID
    private String noticeId;
    //接受人ID
    private String employeeId;
    //阅读标记
    private Integer isRead;
    //阅读时间
    private Date readDate;

    /**
     * 构造方法
     */
    public NoticeRecordDO(){ }
    public NoticeRecordDO(String noticeRecordId,String noticeId,String employeeId,Integer isRead,Date readDate){
        super();
        this.noticeId= noticeId;
        this.employeeId= employeeId;
        this.isRead= isRead;
        this.readDate= readDate;
    }
    /**
     * 设置：主键ID
     */
    public void setNoticeRecordId(String noticeRecordId) {
        this.noticeRecordId = noticeRecordId;
    }
    /**
     * 获取：主键ID
     */
    public String getNoticeRecordId() {
        return noticeRecordId;
    }

    /**
     * 设置：通知通告ID
     */
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }
    /**
     * 获取：通知通告ID
     */
    public String getNoticeId() {
        return noticeId;
    }
    /**
     * 设置：接受人ID
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    /**
     * 获取：接受人ID
     */
    public String getEmployeeId() {
        return employeeId;
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
        return "NoticeRecordDO{" +
                "noticeRecordId=" + noticeRecordId +
                ", noticeId='" + noticeId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", isRead='" + isRead + '\'' +
                ", readDate='" + readDate + '\'' +
                '}';
    }

}
