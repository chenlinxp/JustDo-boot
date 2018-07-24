
package com.justdo.system.dept.domain;

import java.io.Serializable;
import java.util.List;


/**
 * 部门管理
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2018-07-17 22:18:01
 */
public class DeptVO   {
   // private static final long serialVersionUID = 1L;
    //部门ID
    private String deptid;
    //机构ID
    private String organid;
    //上级部门ID
    private String deptpid;
    //部门名称
    private String deptname;
    //
    private List<DeptVO> subdeptvo;

    public String getDeptid() {
        return deptid;
    }
    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }
    public String getOrganid() {
        return organid;
    }
    public void setOrganid(String organid) {
        this.organid = organid;
    }
    public String getDeptpid() {
        return deptpid;
    }
    public void setDeptpid(String deptpid) {
        this.deptpid = deptpid;
    }
    public String getDeptname() {
        return deptname;
    }
    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
    public List<DeptVO> getSubdeptvo() {
        return subdeptvo;
    }
    public void setSubdeptvo(List<DeptVO> subdeptvo) {
        this.subdeptvo = subdeptvo;
    }
}
