package com.justdo.system.province.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * 省份编码表
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:20:40
 */
public class ProvinceDO extends BaseBean {
	
	//
	private String provinceid;
	//编号
	private String provincecode;
	//名称
	private String provincename;

	/**
	 * 设置：
	 */
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	/**
	 * 获取：
	 */
	public String getProvinceid() {
		return provinceid;
	}
	/**
	 * 设置：编号
	 */
	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}
	/**
	 * 获取：编号
	 */
	public String getProvincecode() {
		return provincecode;
	}
	/**
	 * 设置：名称
	 */
	public void setProvincename(String provincename) {
		this.provincename = provincename;
	}
	/**
	 * 获取：名称
	 */
	public String getProvincename() {
		return provincename;
	}
}
