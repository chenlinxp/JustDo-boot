package com.justdo.system.role.domain;

import com.justdo.common.domain.BaseBean;



/**
 * 角色与资源菜单对应关系
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-18 22:48:34
 */
public class RoleResourceDO extends BaseBean {


    //
    private Long roleResourceId;
	//角色ID
	private String roleId;
	//资源菜单ID
	private String resourceId;

    /**
    * 构造方法
    */
    public RoleResourceDO(){ }
    public RoleResourceDO(Long RoleResourceId,String RoleId,String ResourceI){
		super();
			this.roleId= roleId;
			this.resourceId= resourceId;
	}
    /**
     * 设置：
     */
    public void setRoleResourceId(Long roleResourceId) {
        this.roleResourceId = roleResourceId;
    }
    /**
     * 获取：
     */
    public Long getRoleResourceId() {
        return roleResourceId;
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
	/**
	 * 设置：资源菜单ID
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * 获取：资源菜单ID
	 */
	public String getResourceId() {
		return resourceId;
	}

}
