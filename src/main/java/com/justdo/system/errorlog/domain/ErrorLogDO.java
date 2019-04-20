package com.justdo.system.errorlog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justdo.common.domain.BaseBean;



/**
 * 系统错误日志
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-14 18:08:46
 */
public class ErrorLogDO extends BaseBean {


    //主键ID
    private String errorlogId;

	//用户id
	private String userId;
	//用户名
	private String userName;
	//异常信息
	private String exceptionContent;
	//异常状态
	private Integer exceptionState;
	//备注信息
	private String remark;
	//IP地址
	private String ip;
	//解决人姓名
	private String solveMan;
	//解决时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private String solveTime;
	//创建时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private String createTime;

	public ErrorLogDO(){}
    /**
    * 构造方法
    */
    public ErrorLogDO(Long ErrorlogId,Long UserId,String UserName,String ExceptionContent,Integer ExceptionState,String Remark,String Ip,String CreateTime) {
	    super();
	    this.userId = userId;
	    this.userName = userName;
	    this.exceptionContent = exceptionContent;
	    this.exceptionState = exceptionState;
	    this.remark = remark;
	    this.ip = ip;
	    this.createTime = createTime;
    }
	/**
     * 设置：
     */
    public void setErrorlogId(String errorlogId) {
        this.errorlogId = errorlogId;
    }
    /**
     * 获取：
     */
    public String getErrorlogId() {
        return errorlogId;
    }

	/**
	 * 设置：用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置：用户名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 获取：用户名
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置：异常信息
	 */
	public void setExceptionContent(String exceptionContent) {
		this.exceptionContent = exceptionContent;
	}
	/**
	 * 获取：异常信息
	 */
	public String getExceptionContent() {
		return exceptionContent;
	}
	/**
	 * 设置：异常状态
	 */
	public void setExceptionState(Integer exceptionState) {
		this.exceptionState = exceptionState;
	}
	/**
	 * 获取：异常状态
	 */
	public Integer getExceptionState() {
		return exceptionState;
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
	 * 设置：IP地址
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取：IP地址
	 */
	public String getIp() {
		return ip;
	}
	
	public String getSolveMan() {
		return solveMan;
	}

	public void setSolveMan(String solveMan) {
		this.solveMan = solveMan;
	}

	public String getSolveTime() {
		return solveTime;
	}

	public void setSolveTime(String solveTime) {
		this.solveTime = solveTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public String getCreateTime() {
		return createTime;
	}

}
