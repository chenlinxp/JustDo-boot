package com.justdo.system.regexp.domain;

import java.io.Serializable;


/**
 * 系统正则表达式
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-06-26 13:13:39
 */
public class RegexpEXDO extends RegexpDO implements Serializable {
	//序号
	private String rownum;


	/**
	 * 设置：序号
	 */
	public void setRownum(String rid) {
		this.rownum = rownum;
	}
	/**
	 * 获取：序号
	 */
	public String getRownum() {
		return rownum;
	}

}
