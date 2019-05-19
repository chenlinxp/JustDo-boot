package com.justdo.system.operationlog.domain;

import com.justdo.common.domain.BaseBean;


/**
 * 操作日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public class OperationLogDO extends BaseBean {

	private String operationLogId;

	private String userId;

	private String userName;

	private String operation;

	private Integer time;

	private String method;

	private String params;

	private String ip;

	private String createTime;

	public String getOperationLogId() {
		return operationLogId;
	}

	public void setOperationLogId(String operationLogId) {
		this.operationLogId = operationLogId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation == null ? null : operation.trim();
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method == null ? null : method.trim();
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params == null ? null : params.trim();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "LogDO{" +
				"operationLogId=" + operationLogId +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", operation='" + operation + '\'' +
				", time=" + time +
				", method='" + method + '\'' +
				", params='" + params + '\'' +
				", ip='" + ip + '\'' +
				", createTime=" + createTime +
				'}';
	}
}