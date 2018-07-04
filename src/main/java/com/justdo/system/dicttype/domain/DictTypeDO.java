package com.justdo.system.dicttype.domain;

import java.io.Serializable;



/**
 * 字典索引表
 * 
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-21 18:28:07
 */
public class DictTypeDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//uuid
	private String did;

	private String dname;

	private String dcode;

	private String remark;

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDcode() {
		return dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}




}
