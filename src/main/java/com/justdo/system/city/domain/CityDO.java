package com.justdo.system.city.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * 城市编码信息
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:33:37
 */
public class CityDO extends BaseBean {
	
	//
	private String cityid;
	//编码
	private String citycode;
	//名字
	private String cityname;
	//省编号
	private String provincecode;
	//区号
	private String countycode;
	//邮编
	private String zipcode;

	/**
	 * 设置：
	 */
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	/**
	 * 获取：
	 */
	public String getCityid() {
		return cityid;
	}
	/**
	 * 设置：编码
	 */
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	/**
	 * 获取：编码
	 */
	public String getCitycode() {
		return citycode;
	}
	/**
	 * 设置：名字
	 */
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	/**
	 * 获取：名字
	 */
	public String getCityname() {
		return cityname;
	}
	/**
	 * 设置：省编号
	 */
	public void setProvincecode(String provincecode) {
		this.provincecode = provincecode;
	}
	/**
	 * 获取：省编号
	 */
	public String getProvincecode() {
		return provincecode;
	}
	/**
	 * 设置：区号
	 */
	public void setCountycode(String countycode) {
		this.countycode = countycode;
	}
	/**
	 * 获取：区号
	 */
	public String getCountycode() {
		return countycode;
	}
	/**
	 * 设置：邮编
	 */
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	/**
	 * 获取：邮编
	 */
	public String getZipcode() {
		return zipcode;
	}
}
