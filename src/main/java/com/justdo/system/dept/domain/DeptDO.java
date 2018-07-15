package com.justdo.system.dept.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 部门管理
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-07-13 22:18:01
 */
public class DeptDO implements Serializable {
	private static final long serialVersionUID = 1L;

	//部门ID
	private String deptid;
	//机构ID
	private String organid;

	//机构名称
	private String organname;
	//上级部门ID
	private String deptpid;
	//上级部门名称
	private String deptpname;
	//部门名称
	private String deptname;
	//排序
	private Integer ordernum;
	//是否删除  -1：已删除  0：正常
	private Integer delflag;
	//是否有效
	private String isvalidation;

	/**
	 * 设置：部门ID
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	/**
	 * 获取：部门ID
	 */
	public String getDeptid() {
		return deptid;
	}
	/**
	 * 设置：机构ID
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}
	/**
	 * 获取：机构ID
	 */
	public String getOrganid() {
		return organid;
	}
	/**
	 * 设置：机构名称
	 */
	public void setOrganname(String organname) {
		this.organname = organname;
	}
	/**
	 * 获取：机构名称
	 */
	public String getOrganname() {
		return organname;
	}
	/**
	 * 设置：上级部门ID
	 */
	public void setDeptpid(String deptpid) {
		this.deptpid = deptpid;
	}
	/**
	 * 获取：上级部门ID
	 */
	public String getDeptpid() {
		return deptpid;
	}
	/**
	 * 设置：上级部门名称
	 */
	public void setDeptpname(String deptpname) {
		this.deptpname = deptpname;
	}
	/**
	 * 获取：上级部门名称
	 */
	public String getDeptpname() {
		return deptpname;
	}
	/**
	 * 设置：部门名称
	 */
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	/**
	 * 获取：部门名称
	 */
	public String getDeptname() {
		return deptname;
	}
	/**
	 * 设置：排序
	 */
	public void setOrdernum(Integer ordernum) {
		this.ordernum = ordernum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrdernum() {
		return ordernum;
	}
	/**
	 * 设置：是否删除
	 */
	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}
	/**
	 * 获取：是否删除
	 */
	public Integer getDelflag() {
		return delflag;
	}
	/**
	 * 设置：是否有效
	 */
	public void setIsvalidation(String isvalidation) {
		this.isvalidation = isvalidation;
	}
	/**
	 * 获取：是否有效
	 */
	public String getIsvalidation() {
		return isvalidation;
	}
}
