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
    private String emplpoyeeRoleId;

    //员工ID
    private String emplpoyeeId;
    //角色ID
    private String roleId;

    /**
     * 构造方法
     */
    public EmployeeRoleDO(){ }
    public EmployeeRoleDO(String emplpoyeeRoleId,String emplpoyeeId,String RoleI){
        super();
        this.emplpoyeeId= emplpoyeeId;
        this.roleId= roleId;
    }
    /**
     * 设置：
     */
    public void setEmplpoyeeRoleId(String emplpoyeeRoleId) {
        this.emplpoyeeRoleId = emplpoyeeRoleId;
    }
    /**
     * 获取：
     */
    public String getEmplpoyeeRoleId() {
        return emplpoyeeRoleId;
    }

    /**
     * 设置：用户ID
     */
    public void setEmplpoyeeId(String emplpoyeeId) {
        this.emplpoyeeId = emplpoyeeId;
    }
    /**
     * 获取：用户ID
     */
    public String getEmplpoyeeId() {
        return emplpoyeeId;
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
                "emplpoyeeRoleId=" + emplpoyeeRoleId +
                ", emplpoyeeId=" + emplpoyeeId +
                ", roleId=" + roleId +
                '}';
    }
}
