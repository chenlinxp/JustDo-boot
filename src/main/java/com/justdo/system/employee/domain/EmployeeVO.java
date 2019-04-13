package com.justdo.system.employee.domain;

import com.justdo.common.domain.BaseBean;

/**
 * 员工视图类
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public class EmployeeVO extends BaseBean {

    /**
     * 更新的用户对象
     */
    private EmployeeDO employeeDO = new EmployeeDO();
    /**
     * 旧密码
     */
    private String pwdOld;
    /**
     * 新密码
     */
    private String pwdNew;

    public EmployeeDO getEmployeeDO() {
        return employeeDO;
    }

    public void setEmployeeDO(EmployeeDO employeeDO) {
        this.employeeDO = employeeDO;
    }

    public String getPwdOld() {
        return pwdOld;
    }

    public void setPwdOld(String pwdOld) {
        this.pwdOld = pwdOld;
    }

    public String getPwdNew() {
        return pwdNew;
    }

    public void setPwdNew(String pwdNew) {
        this.pwdNew = pwdNew;
    }




}
