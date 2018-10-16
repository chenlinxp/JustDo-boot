package com.justdo.common.domain;

/**
 * 列的属性
 * 
 * 
 */
public class ColumnDO {
	// 列名
	private String columnName;
	// 列名类型
	private String dataType;
	// 列名备注
	private String comments;

	// 属性名称(第一个字母大写)，如：user_name => UserName
	private String attrName;
	// 属性名称(第一个字母小写)，如：user_name => userName
	private String attrname;
	// 属性名称(所有字母小写)，如：user_name => username
	private String attrnametolower;
	// 属性类型
	private String attrType;
	// auto_increment
	private String extra;
	//在界面的展示类型 0：隐藏域，1：文本框，2：单选，3：下拉框，4：多行文本框
	private String displayType;
	//在界面的查询类型 0：不查询，1：文本框，2：下拉框，3：时间文本
	private String searchType;
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAttrname() {
		return attrname;
	}

	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}

	public String getAttrnametolower() {
		return attrnametolower;
	}

	public void setAttrnametolower(String attrnametolower) {
		this.attrnametolower = attrnametolower;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}


	@Override
	public String toString() {
		return "ColumnDO{" +
				"columnName='" + columnName + '\'' +
				", dataType='" + dataType + '\'' +
				", comments='" + comments + '\'' +
				", attrName='" + attrName + '\'' +
				", attrname='" + attrname + '\'' +
				", attrType='" + attrType + '\'' +
				", extra='" + extra + '\'' +
				'}';
	}
}
