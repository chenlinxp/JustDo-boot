package com.justdo.common.domain;

/**
 * 代码生成-数据表
 * Description：
 * @author: chenlin
 * @email: chenlinxp@qq.com
 * @date: 2018-10-16 18:09:51
 * Modification History:
 * Modified by :
 */
public class ColumnDO {
	// 列名
	private String columnName;
	// 列名(所有字母小写)，如：user_name => user_name
	private String columnnametolower;
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
	//在界面的展示类型
	private String displayType;
	//在界面的查询类型
	private String searchType;
	//显示顺序
	private Integer orderNum;
	//字典编码
	private String dictCode;
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnnametolower() {
		return columnnametolower;
	}

	public void setColumnnametolower(String columnnametolower) {
		this.columnnametolower = columnnametolower;
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

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
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
