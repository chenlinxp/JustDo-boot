package com.justdo.system.regexp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justdo.common.domain.BaseBean;




/**
 * 系统正则表达式
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-06-26 13:13:39
 */
public class RegexpDO extends BaseBean {
	
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
	//创建时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private String createTime;
	//修改时间
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private String modifyTime;
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


	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
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
