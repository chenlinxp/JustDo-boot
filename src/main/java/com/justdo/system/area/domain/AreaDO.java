package com.justdo.system.area.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * 地区编码表
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-08-07 13:33:49
 */
public class AreaDO extends BaseBean {
	
	//
	private String areaid;
	//编号
	private String areacode;
	//名字
	private String areaname;
	//城市编号
	private String citycode;
	//区号
	private String countycode;
	//邮编
	private String zipcode;

	/**
	 * 设置：
	 */
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	/**
	 * 获取：
	 */
	public String getAreaid() {
		return areaid;
	}
	/**
	 * 设置：编号
	 */
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	/**
	 * 获取：编号
	 */
	public String getAreacode() {
		return areacode;
	}
	/**
	 * 设置：名字
	 */
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	/**
	 * 获取：名字
	 */
	public String getAreaname() {
		return areaname;
	}
	/**
	 * 设置：城市编号
	 */
	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}
	/**
	 * 获取：城市编号
	 */
	public String getCitycode() {
		return citycode;
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
