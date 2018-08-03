package com.justdo.system.dept.domain;

import com.justdo.common.domain.BaseBean;




/**
 * 部门管理
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-03 15:28:44
 */
public class DeptDO extends BaseBean {

	//部门ID
	private String deptid;
	//机构ID
	private String organid;
	//上级部门ID，一级部门为0
	private String deptpid;
	//部门名称
	private String deptname;
	//部门名称
	private String deptpname;
	//所属机构名称
	private String organname;
	//排序
	private Integer ordernum;
	//是否删除  1：已删除  0：正常
	private Integer delflag;
	//是否有效
	private String isvalidation;
	//部门别名
	private String deptcode;
	//备注
	private String remark;
	//部门负责人
	private String deptman;
	//部门的责人ID
	private String deptmanid;

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
	 * 设置：上级部门ID，一级部门为0
	 */
	public void setDeptpid(String deptpid) {
		this.deptpid = deptpid;
	}
	/**
	 * 获取：上级部门ID，一级部门为0
	 */
	public String getDeptpid() {
		return deptpid;
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
	 * 设置：是否删除  1：已删除  0：正常
	 */
	public void setDelflag(Integer delflag) {
		this.delflag = delflag;
	}
	/**
	 * 获取：是否删除  1：已删除  0：正常
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
	/**
	 * 设置：部门别名
	 */
	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	/**
	 * 获取：部门别名
	 */
	public String getDeptcode() {
		return deptcode;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：部门负责人
	 */
	public void setDeptman(String deptman) {
		this.deptman = deptman;
	}
	/**
	 * 获取：部门负责人
	 */
	public String getDeptman() {
		return deptman;
	}
	/**
	 * 设置：部门的责人ID
	 */
	public void setDeptmanid(String deptmanid) {
		this.deptmanid = deptmanid;
	}
	/**
	 * 获取：部门的责人ID
	 */
	public String getDeptmanid() {
		return deptmanid;
	}
}
