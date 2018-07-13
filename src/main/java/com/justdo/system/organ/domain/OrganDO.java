package com.justdo.system.organ.domain;

import java.io.Serializable;


/**
 * 机构
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-07-06 15:08:01
 */
public class OrganDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//机构ID
	private String organid;
	//机构上级ID
	private String organpid;
	//机构上级名称
	private String organpname;
	//机构名称
	private String organname;
	//机构所属的地区编号
	private String areaid;
	//机构编码
	private String organcode;
	//机构别名
	private String organalias;
	//邮编
	private String postcode;
	//机构地址
	private String address;
	//机构电话
	private String telephone;
	//机构的传真号码
	private String fax;
	//是否有效
	private String isvalidation;
	//负责人姓名
	private String organman;
	//
	private String organmanid;
	//机构备注信息
	private String remark;

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
	 * 设置：机构上级ID
	 */
	public void setOrganpid(String organpid) {
		this.organpid = organpid;
	}
	/**
	 * 获取：机构上级ID
	 */
	public String getOrganpid() {
		return organpid;
	}

	/**
	 * 设置：机构上级名称
	 */
	public void setOrganpname(String organpname) {
		this.organpname = organpname;
	}
	/**
	 * 获取：机构上级名称
	 */
	public String getOrganpname() {
		return organpname;
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
	 * 设置：机构所属的地区编号
	 */
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	/**
	 * 获取：机构所属的地区编号
	 */
	public String getAreaid() {
		return areaid;
	}
	/**
	 * 设置：机构编码
	 */
	public void setOrgancode(String organcode) {
		this.organcode = organcode;
	}
	/**
	 * 获取：机构编码
	 */
	public String getOrgancode() {
		return organcode;
	}
	/**
	 * 设置：机构别名
	 */
	public void setOrganalias(String organalias) {
		this.organalias = organalias;
	}
	/**
	 * 获取：机构别名
	 */
	public String getOrganalias() {
		return organalias;
	}
	/**
	 * 设置：邮编
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	/**
	 * 获取：邮编
	 */
	public String getPostcode() {
		return postcode;
	}
	/**
	 * 设置：机构地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取：机构地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置：机构电话
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 获取：机构电话
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * 设置：机构的传真号码
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * 获取：机构的传真号码
	 */
	public String getFax() {
		return fax;
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
	 * 设置：负责人姓名
	 */
	public void setOrganman(String organman) {
		this.organman = organman;
	}
	/**
	 * 获取：负责人姓名
	 */
	public String getOrganman() {
		return organman;
	}
	/**
	 * 设置：
	 */
	public void setOrganmanid(String organmanid) {
		this.organmanid = organmanid;
	}
	/**
	 * 获取：
	 */
	public String getOrganmanid() {
		return organmanid;
	}
	/**
	 * 设置：机构备注信息
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：机构备注信息
	 */
	public String getRemark() {
		return remark;
	}
}
