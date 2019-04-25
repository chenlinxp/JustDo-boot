package com.justdo.system.notice.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * 通知通告
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-04-25 12:46:25
 */
public class NoticeDO extends BaseBean {


	//主键ID
	private String noticeId;
	//类型
	private String noticeType;
	//标题
	private String noticeTitle;
	//内容
	private String noticeContent;
	//通知排序
	private Integer orderNum;
	//附件
	private String noticeFiles;
	//状态
	private Integer noticeStatus;
	//创建者
	private String createEmployeeId;
	//创建时间
	private Date createTime;
	//修改时间
	private String modifyEmployeeId;
	//更新时间
	private Date modifyTime;
	//备注信息
	private String remark;
	//删除标记
	private Integer delFlag;
	//员工IDs
	private String[] employeeIds;

	/**
	 * 构造方法
	 */
	public NoticeDO(){ }
	public NoticeDO(String noticeId,String noticeType,String noticeTitle,String noticeContent,Integer orderNum,String noticeFiles,Integer noticeStatus,String createEmployeeId,Date createTime,String modifyEmployeeId,Date modifyTime,String remark,Integer delFlag){
		super();
		this.noticeType= noticeType;
		this.noticeTitle= noticeTitle;
		this.noticeContent= noticeContent;
		this.orderNum= orderNum;
		this.noticeFiles= noticeFiles;
		this.noticeStatus= noticeStatus;
		this.createEmployeeId= createEmployeeId;
		this.createTime= createTime;
		this.modifyEmployeeId= modifyEmployeeId;
		this.modifyTime= modifyTime;
		this.remark= remark;
		this.delFlag= delFlag;
	}
	/**
	 * 设置：主键ID
	 */
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}
	/**
	 * 获取：主键ID
	 */
	public String getNoticeId() {
		return noticeId;
	}

	/**
	 * 设置：类型
	 */
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	/**
	 * 获取：类型
	 */
	public String getNoticeType() {
		return noticeType;
	}
	/**
	 * 设置：标题
	 */
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	/**
	 * 获取：标题
	 */
	public String getNoticeTitle() {
		return noticeTitle;
	}
	/**
	 * 设置：内容
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	/**
	 * 获取：内容
	 */
	public String getNoticeContent() {
		return noticeContent;
	}
	/**
	 * 设置：通知排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：通知排序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
	/**
	 * 设置：附件
	 */
	public void setNoticeFiles(String noticeFiles) {
		this.noticeFiles = noticeFiles;
	}
	/**
	 * 获取：附件
	 */
	public String getNoticeFiles() {
		return noticeFiles;
	}
	/**
	 * 设置：状态
	 */
	public void setNoticeStatus(Integer noticeStatus) {
		this.noticeStatus = noticeStatus;
	}
	/**
	 * 获取：状态
	 */
	public Integer getNoticeStatus() {
		return noticeStatus;
	}
	/**
	 * 设置：创建者
	 */
	public void setCreateEmployeeId(String createEmployeeId) {
		this.createEmployeeId = createEmployeeId;
	}
	/**
	 * 获取：创建者
	 */
	public String getCreateEmployeeId() {
		return createEmployeeId;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setModifyEmployeeId(String modifyEmployeeId) {
		this.modifyEmployeeId = modifyEmployeeId;
	}
	/**
	 * 获取：修改时间
	 */
	public String getModifyEmployeeId() {
		return modifyEmployeeId;
	}
	/**
	 * 设置：更新时间
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}
	/**
	 * 设置：备注信息
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注信息
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：删除标记
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：删除标记
	 */
	public Integer getDelFlag() {
		return delFlag;
	}

	public String[] getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(String[] employeeIds) {
		this.employeeIds = employeeIds;
	}
	@Override
	public String toString() {
		return "NoticeDO{" +
				"noticeId=" + noticeId +
				", noticeType='" + noticeType + '\'' +
				", noticeTitle='" + noticeTitle + '\'' +
				", noticeContent='" + noticeContent + '\'' +
				", orderNum='" + orderNum + '\'' +
				", noticeFiles='" + noticeFiles + '\'' +
				", noticeStatus='" + noticeStatus + '\'' +
				", createEmployeeId='" + createEmployeeId + '\'' +
				", createTime='" + createTime + '\'' +
				", modifyEmployeeId='" + modifyEmployeeId + '\'' +
				", modifyTime='" + modifyTime + '\'' +
				", remark='" + remark + '\'' +
				", delFlag='" + delFlag + '\'' +
				'}';
	}

}
