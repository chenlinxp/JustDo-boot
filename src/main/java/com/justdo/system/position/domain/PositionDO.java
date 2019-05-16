package com.justdo.system.position.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * 岗位管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-16 23:21:36
 */
public class PositionDO extends BaseBean {


    //主键ID
    private String postid;

	//上级岗位ID
	private String postpid;
	//部门ID
	private String deptid;
	//机构ID
	private String organid;
	//创建时间
	private Date createTime;
	//修改时间
	private Date modifytime;
	//岗位名称
	private String postname;
	//岗位编号
	private String postcode;
	//是否有效
	private Integer isvalidation;
	//备注
	private String remark;

    /**
    * 构造方法
    */
    public PositionDO(){ }
    public PositionDO(String postid,String postpid,String deptid,String organid,String postname,String postcode,Integer isvalidation,String remark,Date createTime,Date modifytime){
		super();
			this.postpid= postpid;
			this.deptid= deptid;
			this.organid= organid;
			this.createTime= createTime;
			this.modifytime= modifytime;
			this.postname= postname;
			this.postcode= postcode;
			this.isvalidation= isvalidation;
			this.remark= remark;
	}
    /**
     * 设置：主键ID
     */
    public void setPostid(String postid) {
        this.postid = postid;
    }
    /**
     * 获取：主键ID
     */
    public String getPostid() {
        return postid;
    }

	/**
	 * 设置：上级岗位ID
	 */
	public void setPostpid(String postpid) {
		this.postpid = postpid;
	}
	/**
	 * 获取：上级岗位ID
	 */
	public String getPostpid() {
		return postpid;
	}
	/**
	 * 设置：部门ID
	 */
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	/**
	 * 获取：部门ID
	 */
	public String getDeptid() {
		return deptid;
	}
	/**
	 * 设置：机构ID
	 */
	public void setOrganid(String organid) {
		this.organid = organid;
	}
	/**
	 * 获取：机构ID
	 */
	public String getOrganid() {
		return organid;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifytime() {
		return modifytime;
	}
	/**
	 * 设置：岗位名称
	 */
	public void setPostname(String postname) {
		this.postname = postname;
	}
	/**
	 * 获取：岗位名称
	 */
	public String getPostname() {
		return postname;
	}
	/**
	 * 设置：岗位编号
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	/**
	 * 获取：岗位编号
	 */
	public String getPostcode() {
		return postcode;
	}
	/**
	 * 设置：是否有效
	 */
	public void setIsvalidation(Integer isvalidation) {
		this.isvalidation = isvalidation;
	}
	/**
	 * 获取：是否有效
	 */
	public Integer getIsvalidation() {
		return isvalidation;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}

	@Override
	public String toString() {
		return "PositionDO{" +
				"postid=" + postid +
		        ", postpid='" + postpid + '\'' +
		        ", deptid='" + deptid + '\'' +
		        ", organid='" + organid + '\'' +
		        ", createTime='" + createTime + '\'' +
		        ", modifytime='" + modifytime + '\'' +
		        ", postname='" + postname + '\'' +
		        ", postcode='" + postcode + '\'' +
		        ", isvalidation='" + isvalidation + '\'' +
		        ", remark='" + remark + '\'' +
				'}';
	}

}
