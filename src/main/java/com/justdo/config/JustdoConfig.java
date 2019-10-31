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
}
