package com.justdo.system.employee.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justdo.common.domain.BaseBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 员工管理
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:36:21
 */
public class EmployeeDO extends BaseBean {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	//主键ID
	private String employeeId;

	//员工编号
	private String employeeNumber;
	//用户名
	private String loginName;
	//真是姓名
	private String realName;
	//角色
    @JsonIgnore
	private String roleId;
	//密码
	@JsonIgnore
	private String password;
	//密码盐
	@JsonIgnore
	private String passwordSalt;
	//部门ID
	private String deptmentId;
	//部门名称
    private String deptmentName;
	//机构ID
	private String organId;
	//机构名称
	private String organName;
	//岗位ID
	private String positionId;
	//岗位名称
	private String positionName;
	//邮箱
	private String email;
	//手机号
	private String mobile;
	//状态 0:禁用，1:正常
	private Integer employeeState;
	//创建用户id
	private String createEmployeeId;
	//性别
	private Integer employeeSex;
	//出身日期
	private String birthday;
	//照片ID
	private String photoUrl;
	//现居住地
	private String liveAddress;
	//爱好
	private String employeeHobby;
	//省份
	private String province;
	//所在城市
	private String city;
	//所在地区(县)
	private String district;
	//备注
	private String remark;
	//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	//修改时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;


	/**
	 * 构造方法
	 */
	public EmployeeDO(){ }
	public EmployeeDO(String employeeId,String employeeNumber,String LoginName,String RealName,String Password,String PasswordSalt,String DeptmentId,String OrganId,String PositionId,String Email,String Mobile,Integer EmployeeState,String CreateEmployeeId,Date CreateTime,Date ModifyTime,Integer EmployeeSex,String Birthday,String  photoUrl,String LiveAddress,String EmployeeHobby,String Province,String City,String District,String Remar){
		super();
		this.employeeId = employeeId;
		this.employeeNumber= employeeNumber;
		this.loginName= loginName;
		this.realName= realName;
		this.password= password;
		this.passwordSalt= passwordSalt;
		this.deptmentId= deptmentId;
		this.organId= organId;
		this.positionId= positionId;
		this.email= email;
		this.mobile= mobile;
		this.employeeState= employeeState;
		this.createEmployeeId= createEmployeeId;
		this.createTime= createTime;
		this.modifyTime= modifyTime;
		this.employeeSex= employeeSex;
		this.birthday= birthday;
		this.photoUrl= photoUrl;
		this.liveAddress= liveAddress;
		this.employeeHobby= employeeHobby;
		this.province= province;
		this.city= city;
		this.district= district;
		this.remark= remark;
	}
	/**
	 * 设置：主键ID
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * 获取：主键ID
	 */
	public String getEmployeeId() {
		return employeeId;
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
	 * 设置：真是姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 获取：真是姓名
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 设置：角色
	 */
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	/**
	 * 设置：密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * 获取：密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：密码盐
	 */
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	/**
	 * 获取：密码盐
	 */
	public String getPasswordSalt() {
		return passwordSalt;
	}
	/**
	 * 设置：部门ID
	 */
	public void setDeptmentId(String deptmentId) {
		this.deptmentId = deptmentId;
	}
	/**
	 * 获取：部门ID
	 */
	public String getDeptmentId() {
		return deptmentId;
	}
	/**
	 * 获取：部门名称
	 */
	public  String getDeptmentName() {
		return deptmentName;
	}
	/**
	 * 设置：部门名称
	 */
	public void   setDeptmentName(String deptmentName) {
		this.deptmentName = deptmentName;
	}
	/**
	 * 设置：机构ID
	 */
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	/**
	 * 获取：机构ID
	 */
	public String getOrganId() {
		return organId;
	}
	/**
	 * 设置：机构名称
	 */
	public String getOrganName() {
		return organName;
	}
	/**
	 * 获取：机构名称
	 */
	public void   setOrganName(String organName) {
		this.organName = organName;
	}
	/**
	 * 设置：岗位ID
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	/**
	 * 获取：岗位ID
	 */
	public String getPositionId() {
		return positionId;
	}
	/**
	 * 获取：岗位名称
	 */
	public String getPositionName() {
		return positionName;
	}
	/**
	 * 设置：岗位名称
	 */
	public void   setPositionName(String positionName) {
		this.positionName = positionName;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 设置：手机号
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 设置：状态 0:禁用，1:正常
	 */
	public void setEmployeeState(Integer employeeState) {
		this.employeeState = employeeState;
	}
	/**
	 * 获取：状态 0:禁用，1:正常
	 */
	public Integer getEmployeeState() {
		return employeeState;
	}
	/**
	 * 设置：创建用户id
	 */
	public void setCreateEmployeeId(String createEmployeeId) {
		this.createEmployeeId = createEmployeeId;
	}
	/**
	 * 获取：创建用户id
	 */
	public String getCreateEmployeeId() {
		return createEmployeeId;
	}
	/**
	 * 设置：性别
	 */
	public void setEmployeeSex(Integer employeeSex) {
		this.employeeSex = employeeSex;
	}
	/**
	 * 获取：性别
	 */
	public Integer getEmployeeSex() {
		return employeeSex;
	}
	/**
	 * 设置：出身日期
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	/**
	 * 获取：出身日期
	 */
	public String getBirthday() {
		return birthday;
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
	/**
	 * 设置：现居住地
	 */
	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}
	/**
	 * 获取：现居住地
	 */
	public String getLiveAddress() {
		return liveAddress;
	}
	/**
	 * 设置：爱好
	 */
	public void setEmployeeHobby(String employeeHobby) {
		this.employeeHobby = employeeHobby;
	}
	/**
	 * 获取：爱好
	 */
	public String getEmployeeHobby() {
		return employeeHobby;
	}
	/**
	 * 设置：省份
	 */
	public void setProvince(String province) {
		this.province = province;
	}
	/**
	 * 获取：省份
	 */
	public String getProvince() {
		return province;
	}
	/**
	 * 设置：所在城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：所在城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：所在地区(县)
	 */
	public void setDistrict(String district) {
		this.district = district;
	}
	/**
	 * 获取：所在地区(县)
	 */
	public String getDistrict() {
		return district;
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
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getModifyTime() {
		return modifyTime;
	}


	@Override
	public String toString() {
		return "EmployeeDO{" +
				"employeeId=" + employeeId +
				", employeeNumber='" + employeeNumber + '\'' +
				", loginName='" + loginName + '\'' +
				", realName='" + realName + '\'' +
				", password='" + password + '\'' +
				", passwordSalt='" + passwordSalt + '\'' +
				", deptmentId='" + deptmentId + '\'' +
				", organId='" + organId + '\'' +
				", positionId='" + positionId + '\'' +
				", email='" + email + '\'' +
				", mobile='" + mobile + '\'' +
				", employeeState='" + employeeState + '\'' +
				", createEmployeeId='" + createEmployeeId + '\'' +
				", createTime='" + createTime + '\'' +
				", modifyTime='" + modifyTime + '\'' +
				", employeeSex='" + employeeSex + '\'' +
				", birthday='" + birthday + '\'' +
				", photoUrl='" + photoUrl + '\'' +
				", liveAddress='" + liveAddress + '\'' +
				", employeeHobby='" + employeeHobby + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", district='" + district + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}

}
