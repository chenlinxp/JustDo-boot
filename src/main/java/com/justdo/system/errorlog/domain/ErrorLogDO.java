package com.justdo.system.errorlog.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * 系统错误日志
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-14 18:08:46
 */
public class ErrorLogDO extends BaseBean {


    //
    private Long errorlogId;

	//用户id
	private Long userId;
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
	//创建时间
	private Date createTime;

    /**
    * 构造方法
    */
    public ErrorLogDO(Long ErrorlogId,Long UserId,String UserName,String ExceptionContent,Integer ExceptionState,String Remark,String Ip,Date CreateTim) {
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
    public void setErrorlogId(Long errorlogId) {
        this.errorlogId = errorlogId;
    }
    /**
     * 获取：
     */
    public Long getErrorlogId() {
        return errorlogId;
    }

	/**
	 * 设置：用户id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户id
	 */
	public Long getUserId() {
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

}
