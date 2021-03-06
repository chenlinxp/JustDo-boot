package com.justdo.system.employee.domain;

import com.justdo.common.domain.BaseBean;


/**
 * 员工管理
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:36:21
 */
public class SimpleEmployeeDO extends BaseBean {


	//主键ID
	private String id;
	//员工编号
	private String employeeNumber;
	//用户名
	private String loginName;
	//部门ID
	private String deptmentId;
	//机构ID
	private String organId;
	//岗位ID
	private String positionId;
	//照片ID
	private String photoUrl;
	/**
	 * 构造方法
	 */
//	public SimpleEmployeeDO(){ }
//	public SimpleEmployeeDO(String id, String employeeNumber, String loginName, String deptmentId, String organId, String positionId,String photoUrl){
//		super();
//		this.id = id;
//		this.employeeNumber= employeeNumber;
//		this.loginName= loginName;
//		this.deptmentId= deptmentId;
//		this.organId= organId;
//		this.positionId= positionId;
//		this.photoUrl=photoUrl;
//	}
	/**
	 * 设置：主键ID
	 */
	public void setId(String id) {
		this.id =id;
	}
	/**
	 * 获取：主键ID
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置：员工编号
	 */
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	/**
	 * 获取：员工编号
	 */
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	/**
	 * 设置：用户名
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * 获取：用户名
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * 设置：
	 */
	public void setDeptmentId(String deptmentId) {
		this.deptmentId = deptmentId;
	}
	/**
	 * 获取：
	 */
	public String getDeptmentId() {
		return deptmentId;
	}
	/**
	 * 设置：
	 */
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	/**
	 * 获取：
	 */
	public String getOrganId() {
		return organId;
	}
	/**
	 * 设置：
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	/**
	 * 获取：
	 */
	public String getPositionId() {
		return positionId;
	}
	/**
	 * 设置：照片ID
	 */
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	/**
	 * 获取：照片ID
	 */
	public String getPhotoUrl() {
		return photoUrl;
	}

//	@Override
//	public String toString() {
//		return "EmployeeDO{" +
//				"id=" + id +
//				", employeeNumber='" + employeeNumber + '\'' +
//				", loginName='" + loginName + '\'' +
//				", deptmentId='" + deptmentId + '\'' +
//				", organId='" + organId + '\'' +
//				", positionId='" + positionId + '\'' +
//				", photoUrl='" + photoUrl + '\'' +
//				'}';
//	}

}
