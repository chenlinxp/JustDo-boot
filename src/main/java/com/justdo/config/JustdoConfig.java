package com.justdo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="justdo")
public class JustdoConfig {

	//上传路径
	private String uploadPath;

	//日志文件
    private String logfilesPath;

	//APP上传路径
    private String appUploadPath;

	//aapt路径
    private String aaptPath;

	//服务器域名地址
    private String baseAddress;

	//ipa包icon图片处理的shell脚本
    private String pythonShellPath;

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getLogfilesPath() {
		return logfilesPath;
	}

	public void setLogfilesPath(String logfilesPath) {
		this.logfilesPath = logfilesPath;
	}

	public String getAppUploadPath() {
		return appUploadPath;
	}

	public void setAppUploadPath(String appUploadPath) {
		this.appUploadPath = appUploadPath;
	}

	public String getAaptPath() {
		return aaptPath;
	}

	public void setAaptPath(String aaptPath) {
		this.aaptPath = aaptPath;
	}

	public String getBaseAddress() {
		return baseAddress;
	}

	public JustdoConfig setBaseAddress(String baseAddress) {
		this.baseAddress = baseAddress;
		return this;
	}
	public String getPythonShellPath() {
		return pythonShellPath;
	}

	public JustdoConfig setPythonShellPath(String pythonShellPath) {
		this.pythonShellPath = pythonShellPath;
		return this;
	}
}
