package com.justdo.appmanage.app.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.justdo.common.domain.BaseBean;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;



/**
 * APP包管理
 * 
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-05-15 20:17:22
 */
public class AppDO extends BaseBean {


    //主键ID
    private String appId;

	//下载地址
	private String loadUrl;
	//合并的APP_ID
	private String combineAppId;
	//合并的时间
	private Date combineTime;
	//LOGO
	private String iconUrl;
	//二维码图片路径A(8cm)
	private String codeQrA;
	//二维码图片路径B(12cm)
	private String codeQrB;
	//二维码图片路径C(15cm)
	private String codeQrC;
	//创建时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	//修改时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date modifyTime;
	//app显示名称
	private String appName;
	//APP-KEY
	private String appKey;
	//APP项目名称
	private String bundleName;
	//包名ID
	private String bundleId;
	//短链接
	private String shortUrl;
	//过期时间
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	//@JsonDeserialize(using = DateJacksonConverter.class )
	private Date expirerTime;
	//app类型
	private Integer appType;
	//是否合并
	private Integer isCombine;
	//APP介绍
	private String description;

    /**
    * 构造方法
    */
    public AppDO(){ }
    public AppDO(String appId,String appKey,String appName,String bundleName,String bundleId,String shortUrl,String loadUrl,Date expirerTime,Integer appType,Integer isCombine,String combineAppId,Date combineTime,String iconUrl,String codeQrA,String codeQrB,String codeQrC,String description,Date createTime,Date modifyTime){
		super();
			this.loadUrl= loadUrl;
			this.combineAppId= combineAppId;
			this.combineTime= combineTime;
			this.iconUrl= iconUrl;
			this.codeQrA= codeQrA;
			this.codeQrB= codeQrB;
			this.codeQrC= codeQrC;
			this.createTime= createTime;
			this.modifyTime= modifyTime;
			this.appName= appName;
			this.appKey= appKey;
			this.bundleName= bundleName;
			this.bundleId= bundleId;
			this.shortUrl= shortUrl;
			this.expirerTime= expirerTime;
			this.appType= appType;
			this.isCombine= isCombine;
			this.description= description;
	}
    /**
     * 设置：主键ID
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }
    /**
     * 获取：主键ID
     */
    public String getAppId() {
        return appId;
    }

	/**
	 * 设置：下载地址
	 */
	public void setLoadUrl(String loadUrl) {
		this.loadUrl = loadUrl;
	}
	/**
	 * 获取：下载地址
	 */
	public String getLoadUrl() {
		return loadUrl;
	}
	/**
	 * 设置：合并的APP_ID
	 */
	public void setCombineAppId(String combineAppId) {
		this.combineAppId = combineAppId;
	}
	/**
	 * 获取：合并的APP_ID
	 */
	public String getCombineAppId() {
		return combineAppId;
	}
	/**
	 * 设置：合并的时间
	 */
	public void setCombineTime(Date combineTime) {
		this.combineTime = combineTime;
	}
	/**
	 * 获取：合并的时间
	 */
	public Date getCombineTime() {
		return combineTime;
	}
	/**
	 * 设置：LOGO
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	/**
	 * 获取：LOGO
	 */
	public String getIconUrl() {
		return iconUrl;
	}
	/**
	 * 设置：二维码图片路径A(8cm)
	 */
	public void setCodeQrA(String codeQrA) {
		this.codeQrA = codeQrA;
	}
	/**
	 * 获取：二维码图片路径A(8cm)
	 */
	public String getCodeQrA() {
		return codeQrA;
	}
	/**
	 * 设置：二维码图片路径B(12cm)
	 */
	public void setCodeQrB(String codeQrB) {
		this.codeQrB = codeQrB;
	}
	/**
	 * 获取：二维码图片路径B(12cm)
	 */
	public String getCodeQrB() {
		return codeQrB;
	}
	/**
	 * 设置：二维码图片路径C(15cm)
	 */
	public void setCodeQrC(String codeQrC) {
		this.codeQrC = codeQrC;
	}
	/**
	 * 获取：二维码图片路径C(15cm)
	 */
	public String getCodeQrC() {
		return codeQrC;
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
	 * 设置：app显示名称
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * 获取：app显示名称
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * 设置：APP-KEY
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	/**
	 * 获取：APP-KEY
	 */
	public String getAppKey() {
		return appKey;
	}
	/**
	 * 设置：APP项目名称
	 */
	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}
	/**
	 * 获取：APP项目名称
	 */
	public String getBundleName() {
		return bundleName;
	}
	/**
	 * 设置：包名ID
	 */
	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}
	/**
	 * 获取：包名ID
	 */
	public String getBundleId() {
		return bundleId;
	}
	/**
	 * 设置：短链接
	 */
	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	/**
	 * 获取：短链接
	 */
	public String getShortUrl() {
		return shortUrl;
	}
	/**
	 * 设置：过期时间
	 */
	public void setExpirerTime(Date expirerTime) {
		this.expirerTime = expirerTime;
	}
	/**
	 * 获取：过期时间
	 */
	public Date getExpirerTime() {
		return expirerTime;
	}
	/**
	 * 设置：app类型
	 */
	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	/**
	 * 获取：app类型
	 */
	public Integer getAppType() {
		return appType;
	}
	/**
	 * 设置：是否合并
	 */
	public void setIsCombine(Integer isCombine) {
		this.isCombine = isCombine;
	}
	/**
	 * 获取：是否合并
	 */
	public Integer getIsCombine() {
		return isCombine;
	}
	/**
	 * 设置：APP介绍
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：APP介绍
	 */
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "AppDO{" +
				"appId=" + appId +
		        ", loadUrl='" + loadUrl + '\'' +
		        ", combineAppId='" + combineAppId + '\'' +
		        ", combineTime='" + combineTime + '\'' +
		        ", iconUrl='" + iconUrl + '\'' +
		        ", codeQrA='" + codeQrA + '\'' +
		        ", codeQrB='" + codeQrB + '\'' +
		        ", codeQrC='" + codeQrC + '\'' +
		        ", createTime='" + createTime + '\'' +
		        ", modifyTime='" + modifyTime + '\'' +
		        ", appName='" + appName + '\'' +
		        ", appKey='" + appKey + '\'' +
		        ", bundleName='" + bundleName + '\'' +
		        ", bundleId='" + bundleId + '\'' +
		        ", shortUrl='" + shortUrl + '\'' +
		        ", expirerTime='" + expirerTime + '\'' +
		        ", appType='" + appType + '\'' +
		        ", isCombine='" + isCombine + '\'' +
		        ", description='" + description + '\'' +
				'}';
	}

}
