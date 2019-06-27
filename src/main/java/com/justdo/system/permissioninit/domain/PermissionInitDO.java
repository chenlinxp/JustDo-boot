package com.justdo.system.permissioninit.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * shiro初始权限
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-06-27 12:44:13
 */
public class PermissionInitDO extends BaseBean {


    //主键ID
    private String permissionId;

	//创建时间
	private Date createTime;
	//修改时间
	private Date modifyTime;
	//链接地址
	private String permissionUrl;
	//需要具备的权限
	private String permissionInit;
	//排序
	private Integer permissionSort;
	//备注
	private String remark;

    /**
    * 构造方法
    */
    public PermissionInitDO(){ }
    public PermissionInitDO(String permissionId,String permissionUrl,String permissionInit,Integer permissionSort,String remark,Date createTime,Date modifyTime){
		super();
			this.createTime= createTime;
			this.modifyTime= modifyTime;
			this.permissionUrl= permissionUrl;
			this.permissionInit= permissionInit;
			this.permissionSort= permissionSort;
			this.remark= remark;
	}
    /**
     * 设置：主键ID
     */
    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }
    /**
     * 获取：主键ID
     */
    public String getPermissionId() {
        return permissionId;
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
	/**
	 * 设置：链接地址
	 */
	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}
	/**
	 * 获取：链接地址
	 */
	public String getPermissionUrl() {
		return permissionUrl;
	}
	/**
	 * 设置：需要具备的权限
	 */
	public void setPermissionInit(String permissionInit) {
		this.permissionInit = permissionInit;
	}
	/**
	 * 获取：需要具备的权限
	 */
	public String getPermissionInit() {
		return permissionInit;
	}
	/**
	 * 设置：排序
	 */
	public void setPermissionSort(Integer permissionSort) {
		this.permissionSort = permissionSort;
	}
	/**
	 * 获取：排序
	 */
	public Integer getPermissionSort() {
		return permissionSort;
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
		return "PermissionInitDO{" +
				"permissionId=" + permissionId +
		        ", createTime='" + createTime + '\'' +
		        ", modifyTime='" + modifyTime + '\'' +
		        ", permissionUrl='" + permissionUrl + '\'' +
		        ", permissionInit='" + permissionInit + '\'' +
		        ", permissionSort='" + permissionSort + '\'' +
		        ", remark='" + remark + '\'' +
				'}';
	}

}
