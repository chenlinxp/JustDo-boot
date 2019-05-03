package com.justdo.common.domain;

/**
 * 代码生成-列的生成实体
 * Description：
 * @author: chenlin
 * @email: chenlinxp@qq.com
 * @date: 2018-10-16 18:09:51
 * Modification History:
 * Modified by :
 */
public class ColumnVO {

	//列的名称
	private String columnName;
	//列的注释
	private String columnComment;
	//列的数据类型
	private String dataType;
	//显示方式
	private String displayType;
    //查询方式
	private String searchType;
	//显示顺序
	private Integer orderNum;


	public  String  getColumnName() {
		return columnName;
	}public void    setColumnName(String columnName) {
		this.columnName = columnName;
	}public String  getColumnComment() {
		return columnComment;
	}public void    setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}public String  getDataType() {
		return dataType;
	}public void    setDataType(String dataType) {
		this.dataType = dataType;
	}public String  getDisplayType() {
		return displayType;
	}public void    setDisplayType(String displayType) {
		this.displayType = displayType;
	}public String  getSearchType() {
		return searchType;
	}public void    setSearchType(String searchType) {
		this.searchType = searchType;
	}public Integer getOrderNum() {
		return orderNum;
	}public void    setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
}
