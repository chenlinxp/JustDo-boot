package com.justdo.system.role.domain;

import com.justdo.common.domain.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * 角色
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public class RoleDO extends BaseBean {

	//
	private String roleId;
	//角色名称
	private String roleName;
	//角色标识
	private String roleSign;
	//角色状态
	private Integer roleState;
	//备注
	private String remark;
	//创建用户id
	private String createEmployeeId;
	//创建时间
	private Date createTime;
	//创建时间
	private Date modifyTime;

	private List<String> resourceIds;

	public  String     getRoleId() {
		return roleId;
	}
	public void         setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String       getRoleName() {
		return roleName;
	}
	public void         setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String       getRoleSign() {
		return roleSign;
	}
	public void         setRoleSign(String roleSign) {
		this.roleSign = roleSign;
	}
	public Integer      getRoleState() {
		return roleState;
	}
	public void         setRoleState(Integer roleState) {
		this.roleState = roleState;
	}
	public String       getRemark() {
		return remark;
	}
	public void         setRemark(String remark) {
		this.remark = remark;
	}
	public String         getCreateEmployeeId() {
		return createEmployeeId;
	}
	public void         setCreateEmployeeId(String createEmployeeId) {
		this.createEmployeeId = createEmployeeId;
	}
	public Date    getCreateTime() {
		return createTime;
	}
	public void         setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date    getModifyTime() {
		return modifyTime;
	}
	public void         setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public List<String> getResourceIds() {
		return resourceIds;
	}
	public void         setResourceIds(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}

	@Override
	public String toString() {
		return "RoleDO{" +
				"roleId=" + roleId +
				", roleName='" + roleName + '\'' +
				", roleSign='" + roleSign + '\'' +
				", remark='" + remark + '\'' +
				", createEmployeeId=" + createEmployeeId +
				", createTime=" + createTime +
				", modifyTime=" + modifyTime +
				", resourceIds=" + resourceIds +
				'}';
	}
}
