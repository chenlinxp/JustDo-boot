package com.justdo.system.organ.domain;


import java.util.List;

import com.justdo.common.domain.BaseBean;
import com.justdo.system.dept.domain.DeptVO;

/**
 * Created by chenlin on 2018/7/17.
 */
public class OrganDeptVO extends BaseBean {
    private String organid;

    private String organpid;

    private String organname;

    private List<OrganDeptVO> suborgandeptvo ;

    private List<DeptVO> deptvo;

    public String getOrganid() {
        return organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getOrganpid() {
        return organpid;
    }

    public void setOrganpid(String organpid) {
        this.organpid = organpid;
    }

    public String getOrganname() {
        return organname;
    }

    public void setOrganname(String organname) {
        this.organname = organname;
    }

    public List<OrganDeptVO> getSuborgandeptvo() {
        return suborgandeptvo;
    }

    public void setSuborgandeptvo(List<OrganDeptVO> suborgandeptvo) {
        this.suborgandeptvo = suborgandeptvo;
    }

    public List<DeptVO> getDeptvo() {
        return deptvo;
    }

    public void setDeptvo(List<DeptVO> deptvo) {
        this.deptvo = deptvo;
    }



}
