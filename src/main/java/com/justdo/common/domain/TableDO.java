package com.justdo.common.domain;

import java.util.List;

/**
 * 代码生成-数据表
 * Description：
 * @author: chenlin
 * @email: chenlinxp@qq.com
 * @date: 2018-10-16 18:09:51
 * Modification History:
 * Modified by :
 */
public class TableDO {
    //表的名称
    private String tableName;
    //表的备注
    private String comments;
    //表的主键
    private ColumnDO pk;
    //表的列名(不包含主键)
    private List<ColumnDO> columns;

    //类名(第一个字母大写)，如：sys_user => SysUser
    private String className;
    //类名(第一个字母小写)，如：sys_user => sysUser
    private String classname;
    // 类名(所有字母小写)，如：sys_user => sysuser
    private String classnametolower;
    //构造函数的参数
    private String constructorParams;
    //是否生成移动端的crud接口
    private int  appInterface;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnDO getPk() {
        return pk;
    }

    public void setPk(ColumnDO pk) {
        this.pk = pk;
    }

    public List<ColumnDO> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnDO> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }
    public String getClassnametolower() {
        return classnametolower;
    }

    public void setClassnametolower(String classnametolower) {
        this.classnametolower = classnametolower;
    }
    public String getConstructorParams() {
        return constructorParams;
    }

    public void setConstructorParams(String constructorParams) {
        this.constructorParams = constructorParams;
    }

    public int getAppInterface() {
        return appInterface;
    }

    public void setAppInterface(int appInterface) {
        this.appInterface = appInterface;
    }
    @Override
    public String toString() {
        return "TableDO{" +
                "tableName='" + tableName + '\'' +
                ", comments='" + comments + '\'' +
                ", pk=" + pk +
                ", columns=" + columns +
                ", className='" + className + '\'' +
                ", classname='" + classname + '\'' +
                '}';
    }
}
