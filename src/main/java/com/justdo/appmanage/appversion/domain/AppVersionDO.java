package com.justdo.appmanage.appversion.domain;

import com.justdo.common.domain.BaseBean;
import java.util.Date;



/**
 * APP包版本记录管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-18 13:10:53
 */
public class AppVersionDO extends BaseBean {


    //主键ID
    private String appVersionId;

	//APP-ID
	private String appId;
	//删除标识
	private Integer delFlag;
	//创建时间
	private Date createTime;
	//版本号
	private String versionCode;
	//构建号
	private String buildCode;
	//APP大小
	private String appSizes;
	//总有下载次数
	private Integer totalLoadNumber;
	//显示状态
	private Integer displayState;
	//今天下载次数
	private Integer todayLoadNumber;
	//版本描述
	private String versionDescription;
	//更新描述
	private String updateDescription;
	//二维码
	private String codeQr;
    //下载页面
	private String loadUrl;
	//下载地址
	private String downloadUrl;
    /**
    * 构造方法
    */
    public AppVersionDO(){ }
    public AppVersionDO(String appVersionId,String appId,String versionCode,String buildCode,String appSizes,Integer totalLoadNumber,Integer displayState,Integer delFlag,Integer todayLoadNumber,String versionDescription,String updateDescription,Date createTime,String loadUrl,String codeQr,String downloadUrl){

	        this.appVersionId= appVersionId;
			this.appId= appId;
			this.delFlag= delFlag;
			this.createTime= createTime;
			this.versionCode= versionCode;
			this.buildCode= buildCode;
			this.appSizes= appSizes;
			this.totalLoadNumber= totalLoadNumber;
			this.displayState= displayState;
			this.todayLoadNumber= todayLoadNumber;
			this.versionDescription= versionDescription;
			this.updateDescription= updateDescription;
			this.loadUrl = loadUrl;
			this.codeQr = codeQr;
			this.downloadUrl = downloadUrl;
	}
    /**
     * 设置：主键ID
     */
    public void setAppVersionId(String appVersionId) {
        this.appVersionId = appVersionId;
    }
    /**
     * 获取：主键ID
     */
    public String getAppVersionId() {
        return appVersionId;
    }

	/**
	 * 设置：APP-ID
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	/**
	 * 获取：APP-ID
	 */
	public String getAppId() {
		return appId;
	}
	/**
	 * 设置：删除标识
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	/**
	 * 获取：删除标识
	 */
	public Integer getDelFlag() {
		return delFlag;
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
	 * 设置：版本号
	 */
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	/**
	 * 获取：版本号
	 */
	public String getVersionCode() {
		return versionCode;
	}
	/**
	 * 设置：构建号
	 */
	public void setBuildCode(String buildCode) {
		this.buildCode = buildCode;
	}
	/**
	 * 获取：构建号
	 */
	public String getBuildCode() {
		return buildCode;
	}
	/**
	 * 设置：APP大小
	 */
	public void setAppSizes(String appSizes) {
		this.appSizes = appSizes;
	}
	/**
	 * 获取：APP大小
	 */
	public String getAppSizes() {
		return appSizes;
	}
	/**
	 * 设置：总有下载次数
	 */
	public void setTotalLoadNumber(Integer totalLoadNumber) {
		this.totalLoadNumber = totalLoadNumber;
	}
	/**
	 * 获取：总有下载次数
	 */
	public Integer getTotalLoadNumber() {
		return totalLoadNumber;
	}
	/**
	 * 设置：显示状态
	 */
	public void setDisplayState(Integer displayState) {
		this.displayState = displayState;
	}
	/**
	 * 获取：显示状态
	 */
	public Integer getDisplayState() {
		return displayState;
	}
	/**
	 * 设置：今天下载次数
	 */
	public void setTodayLoadNumber(Integer todayLoadNumber) {
		this.todayLoadNumber = todayLoadNumber;
	}
	/**
	 * 获取：今天下载次数
	 */
	public Integer getTodayLoadNumber() {
		return todayLoadNumber;
	}
	/**
	 * 设置：版本描述
	 */
	public void setVersionDescription(String versionDescription) {
		this.versionDescription = versionDescription;
	}
	/**
	 * 获取：版本描述
	 */
	public String getVersionDescription() {
		return versionDescription;
	}
	/**
	 * 设置：更新描述
	 */
	public void setUpdateDescription(String updateDescription) {
		this.updateDescription = updateDescription;
	}
	/**
	 * 获取：更新描述
	 */
	public String getUpdateDescription() {
		return updateDescription;
	}

	public String getLoadUrl() {
		return loadUrl;
	}

	public void setLoadUrl(String loadUrl) {
		this.loadUrl = loadUrl;
	}


	public String getCodeQr() {
		return codeQr;
	}

	public void setCodeQr(String codeQr) {
		this.codeQr = codeQr;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public AppVersionDO setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
		return this;
	}
	@Override
	public String toString() {
		return "AppVersionDO{" +
				"appVersionId=" + appVersionId +
		        ", appId='" + appId + '\'' +
		        ", delFlag='" + delFlag + '\'' +
		        ", createTime='" + createTime + '\'' +
		        ", versionCode='" + versionCode + '\'' +
		        ", buildCode='" + buildCode + '\'' +
		        ", appSizes='" + appSizes + '\'' +
		        ", totalLoadNumber='" + totalLoadNumber + '\'' +
		        ", displayState='" + displayState + '\'' +
		        ", todayLoadNumber='" + todayLoadNumber + '\'' +
		        ", versionDescription='" + versionDescription + '\'' +
		        ", updateDescription='" + updateDescription + '\'' +
				'}';
	}

}
