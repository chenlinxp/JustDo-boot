package com.justdo.system.regexp.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 系统正则表达式
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-06-26 13:13:39
 */
public class RegexpDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键
	private String rid;
	//正则的名称
	private String rname;
	//正则的代码
	private String rcode;
	//正则的内容
	private String rcontent;
	//备注信息
	private String remark;
	//是否有效
	private Integer rvalid;

	/**
	 * 设置：主键
	 */
	public void setRid(String rid) {
		this.rid = rid;
	}
	/**
	 * 获取：主键
	 */
	public String getRid() {
		return rid;
	}
	/**
	 * 设置：正则的名称
	 */
	public void setRname(String rname) {
		this.rname = rname;
	}
	/**
	 * 获取：正则的名称
	 */
	public String getRname() {
		return rname;
	}
	/**
	 * 设置：正则的代码
	 */
	public void setRcode(String rcode) {
		this.rcode = rcode;
	}
	/**
	 * 获取：正则的代码
	 */
	public String getRcode() {
		return rcode;
	}
	/**
	 * 设置：正则的内容
	 */
	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}
	/**
	 * 获取：正则的内容
	 */
	public String getRcontent() {
		return rcontent;
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
	 * 设置：是否有效
	 */
	public void setRvalid(Integer rvalid) {
		this.rvalid = rvalid;
	}
	/**
	 * 获取：是否有效
	 */
	public Integer getRvalid() {
		return rvalid;
	}
}
