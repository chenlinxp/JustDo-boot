package com.justdo.system.dict.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * 数据字典的内容
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-02 11:29:02
 */
public class DictContentDO extends BaseBean {

	//数据字典表ID
	private String dcid;
	//数据字典索引表的ID
	private String did;
	//数据字典的内容
	private String dcvalue;
	//数据字典编码
	private String dccode;
	//说明
	private String remark;
	//数据字典排序号
	private Integer orderno;
	//是否有效
	private Integer dcvalid;

	/**
	 * 设置：数据字典表ID
	 */
	public void setDcid(String dcid) {
		this.dcid = dcid;
	}
	/**
	 * 获取：数据字典表ID
	 */
	public String getDcid() {
		return dcid;
	}
	/**
	 * 设置：数据字典索引表的ID
	 */
	public void setDid(String did) {
		this.did = did;
	}
	/**
	 * 获取：数据字典索引表的ID
	 */
	public String getDid() {
		return did;
	}
	/**
	 * 设置：数据字典的内容
	 */
	public void setDcvalue(String dcvalue) {
		this.dcvalue = dcvalue;
	}
	/**
	 * 获取：数据字典的内容
	 */
	public String getDcvalue() {
		return dcvalue;
	}
	/**
	 * 设置：数据字典编码
	 */
	public void setDccode(String dccode) {
		this.dccode = dccode;
	}
	/**
	 * 获取：数据字典编码
	 */
	public String getDccode() {
		return dccode;
	}
	/**
	 * 设置：说明
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：说明
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：数据字典排序号
	 */
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	/**
	 * 获取：数据字典排序号
	 */
	public Integer getOrderno() {
		return orderno;
	}
	/**
	 * 设置：是否有效
	 */
	public void setDcvalid(Integer dcvalid) {
		this.dcvalid = dcvalid;
	}
	/**
	 * 获取：是否有效
	 */
	public Integer getDcvalid() {
		return dcvalid;
	}
}
