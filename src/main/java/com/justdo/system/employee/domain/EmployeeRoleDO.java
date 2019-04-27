package com.justdo.system.employee.domain;

import com.justdo.common.domain.BaseBean;


/**
 * 用户与角色对应关系
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-18 22:49:30
 */
public class EmployeeRoleDO extends BaseBean {


    //主键ID
    private String employeeRoleId;

    //员工ID
    private String employeeId;
    //角色ID
    private String roleId;

    /**
     * 构造方法
     */
    public EmployeeRoleDO(){ }
    public EmployeeRoleDO(String employeeRoleId,String employeeId,String RoleI){
        super();
        this.employeeId= employeeId;
        this.roleId= roleId;
    }
    /**
     * 设置：
     */
    public void setEmployeeRoleId(String employeeRoleId) {
        this.employeeRoleId = employeeRoleId;
    }
    /**
     * 获取：
     */
    public String getEmployeeRoleId() {
        return employeeRoleId;
    }

    /**
     * 设置：用户ID
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    /**
     * 获取：用户ID
     */
    public String getEmployeeId() {
        return employeeId;
    }
    /**
     * 设置：角色ID
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    /**
     * 获取：角色ID
     */
    public String getRoleId() {
        return roleId;
    }
    @Override
    public String toString() {
        return "EmplpoyeeRoleDO{" +
                "employeeRoleId=" + employeeRoleId +
                ", employeeId=" + employeeId +
                ", roleId=" + roleId +
                '}';
    }
}
