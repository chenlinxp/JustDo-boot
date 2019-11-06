package com.justdo.system.desksettings.domain;

import com.justdo.common.domain.BaseBean;



/**
 * 桌面设置
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-11-06 12:17:25
 */
public class DeskSettingsDO extends BaseBean {


    //设置ID
    private String settingsId;

	//员工ID
	private String employeeId;
	//主题设置
	private String settingsItem;
	//是否顶部菜单固定
	private Integer fixedNavBar;
	//是否收起左侧菜单
	private Integer collapsMenu;
	//是否固定宽度
	private Integer boxedLayout;

    /**
    * 构造方法
    */
    public DeskSettingsDO(){ }
    public DeskSettingsDO(String employeeId,String settingsId,String settingsItem,Integer fixedNavBar,Integer collapsMenu,Integer boxedLayout){
		super();
			this.employeeId= employeeId;
			this.settingsItem= settingsItem;
			this.fixedNavBar= fixedNavBar;
			this.collapsMenu= collapsMenu;
			this.boxedLayout= boxedLayout;
	}
    /**
     * 设置：设置ID
     */
    public void setSettingsId(String settingsId) {
        this.settingsId = settingsId;
    }
    /**
     * 获取：设置ID
     */
    public String getSettingsId() {
        return settingsId;
    }

	/**
	 * 设置：员工ID
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * 获取：员工ID
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * 设置：主题设置
	 */
	public void setSettingsItem(String settingsItem) {
		this.settingsItem = settingsItem;
	}
	/**
	 * 获取：主题设置
	 */
	public String getSettingsItem() {
		return settingsItem;
	}
	/**
	 * 设置：是否顶部菜单固定
	 */
	public void setFixedNavBar(Integer fixedNavBar) {
		this.fixedNavBar = fixedNavBar;
	}
	/**
	 * 获取：是否顶部菜单固定
	 */
	public Integer getFixedNavBar() {
		return fixedNavBar;
	}
	/**
	 * 设置：是否收起左侧菜单
	 */
	public void setCollapsMenu(Integer collapsMenu) {
		this.collapsMenu = collapsMenu;
	}
	/**
	 * 获取：是否收起左侧菜单
	 */
	public Integer getCollapsMenu() {
		return collapsMenu;
	}
	/**
	 * 设置：是否固定宽度
	 */
	public void setBoxedLayout(Integer boxedLayout) {
		this.boxedLayout = boxedLayout;
	}
	/**
	 * 获取：是否固定宽度
	 */
	public Integer getBoxedLayout() {
		return boxedLayout;
	}

	@Override
	public String toString() {
		return "DeskSettingsDO{" +
				"settingsId=" + settingsId +
		        ", employeeId='" + employeeId + '\'' +
		        ", settingsItem='" + settingsItem + '\'' +
		        ", fixedNavBar='" + fixedNavBar + '\'' +
		        ", collapsMenu='" + collapsMenu + '\'' +
		        ", boxedLayout='" + boxedLayout + '\'' +
				'}';
	}

}
