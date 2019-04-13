package com.justdo.system.employee.domain;



import com.justdo.common.domain.BaseBean;

import java.util.Date;

/**
 * 在线员工
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public class EmployeeOnline extends BaseBean {

	/**
	 *
	 */
	private String id;

	/**
	 * 员工ID
	 */
	private String employeeId;

	/**
	 * 员工编号
	 */
	private String employeeNum;

	/**
	 * 登陆名
	 */
	private String loginName;

	/**
	 * 真实名称
	 */
	private String realName;

	/**
	 * 用户主机IP地址
	 */
	private String hostIP;

	/**
	 * 用户主机MAC地址
	 */
	private String hostMAC;

	/**
	 * 用户浏览器类型
	 */
	private String userAgent;

	/**
	 * 在线状态
	 */
	private String status = "on_line";

	/**
	 * session创建时间
	 */
	private Date startTime;

	/**
	 * session最后访问时间
	 */
	private Date lastTime;

	/**
	 * 超时时间
	 */
	private Long timeout;

	/**
	 * 备份的当前用户会话
	 */
	private String onlineSession;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeNum() {
		return employeeNum;
	}

	public void setEmployeeNum(String employeeNum) {
		this.employeeNum = employeeNum;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getHostIP() {
		return hostIP;
	}

	public void setHostIP(String hostIP) {
		this.hostIP = hostIP;
	}

	public String getHostMAC() {
		return hostMAC;
	}

	public void setHostMAC(String hostMAC) {
		this.hostMAC = hostMAC;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public String getOnlineSession() {
		return onlineSession;
	}

	public void setOnlineSession(String onlineSession) {
		this.onlineSession = onlineSession;
	}







}
