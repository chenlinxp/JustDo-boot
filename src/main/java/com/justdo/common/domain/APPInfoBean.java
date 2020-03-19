package com.justdo.common.domain;

/**
 * APP包的信息
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/3/15 下午4:28
 */
public class APPInfoBean extends BaseBean {

	private String appName;

	private String packageName;

	private String versionName;

	private String VersionCode;

	private String logoImage;

	private String bundleName ;

	private String appType;

	private String fileSize;


	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionCode() {
		return VersionCode;
	}

	public void setVersionCode(String versionCode) {
		VersionCode = versionCode;
	}

	public String getLogoImage() {
		return logoImage;
	}

	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getAppType() {
		return appType;
	}

	public APPInfoBean setAppType(String appType) {
		this.appType = appType;
		return this;
	}
}
