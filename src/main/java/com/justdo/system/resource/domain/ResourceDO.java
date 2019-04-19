package com.justdo.system.resource.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * 资源菜单管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-18 22:48:34
 */
public class ResourceDO extends BaseBean {


    //
    private String resourceId;

	//父菜单ID，一级菜单为0
	private String parentId;
	//菜单名称
	private String resourceName;
	//菜单URL
	private String resourceUrl;
	//授权(多个用逗号分隔，如：esource:list,esource:add)
	private String resourcePermission;
	//类型   0：目录   1：菜单   2：按钮
	private Integer resourceType;
	//菜单图标
	private String resourceIcon;

	//桌面显示
	private Integer deskDisplay;
	//排序
	private Integer orderNum;
	//创建时间
	private Date createTime;
	//修改时间
	private Date modifyTime;

    /**
    * 构造方法
    */
    public ResourceDO(){ }
    public ResourceDO(String ResourceId,String ParentId,String ResourceName,String ResourceUrl,String ResourcePermission,Integer ResourceType,String ResourceIcon,Integer deskDisply,Integer OrderNum,Date CreateTime,Date ModifyTime){
		super();
			this.parentId= parentId;
			this.resourceName= resourceName;
			this.resourceUrl= resourceUrl;
			this.resourcePermission= resourcePermission;
			this.resourceType= resourceType;
			this.resourceIcon= resourceIcon;
			this.orderNum= orderNum;
			this.createTime= createTime;
			this.modifyTime= modifyTime;
	}
    /**
     * 设置：
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    /**
     * 获取：
     */
    public String getResourceId() {
        return resourceId;
    }

	/**
	 * 设置：父菜单ID，一级菜单为0
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父菜单ID，一级菜单为0
	 */
	public String getParentId() {
		return parentId;
	}
	/**
	 * 设置：菜单名称
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	/**
	 * 获取：菜单名称
	 */
	public String getResourceName() {
		return resourceName;
	}
	/**
	 * 设置：菜单URL
	 */
	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}
	/**
	 * 获取：菜单URL
	 */
	public String getResourceUrl() {
		return resourceUrl;
	}
	/**
	 * 设置：授权(多个用逗号分隔，如：user:list,user:create)
	 */
	public void setResourcePermission(String resourcePermission) {
		this.resourcePermission = resourcePermission;
	}
	/**
	 * 获取：授权(多个用逗号分隔，如：user:list,user:create)
	 */
	public String getResourcePermission() {
		return resourcePermission;
	}
	/**
	 * 设置：类型   0：目录   1：菜单   2：按钮
	 */
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}
	/**
	 * 获取：类型   0：目录   1：菜单   2：按钮
	 */
	public Integer getResourceType() {
		return resourceType;
	}
	/**
	 * 设置：菜单图标
	 */
	public void setResourceIcon(String resourceIcon) {
		this.resourceIcon = resourceIcon;
	}
	/**
	 * 获取：菜单图标
	 */
	public String getResourceIcon() {
		return resourceIcon;
	}
	/**
	 * 获取：桌面显示
	 */
	public Integer getDeskDisplay() {
		return deskDisplay;
	}
	/**
	 * 设置：桌面显示
	 */
	public void setDeskDisplay(Integer deskDisplay) {
		this.deskDisplay = deskDisplay;
	}
	/**
	 * 设置：排序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	/**
	 * 获取：排序
	 */
	public Integer getOrderNum() {
		return orderNum;
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

}
