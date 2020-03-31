package com.justdo.common.controller;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.StringUtils;
import com.justdo.config.JustdoConfig;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;

/**
 *
 * 获取日志文件Controller
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */
@Controller
@RequestMapping("/system/logfiles")
public class GetLogController {

	private String preUrl="system/logfiles"  ;

	@Autowired
	JustdoConfig justdoConfig;
	/**
	 * 获取日志页面
	 * @return 获取日志页面路径
	 */
	@GetMapping()
	@RequiresPermissions("system:logfiles")
	String OperationLog(){
		return preUrl + "/logfiles";
	}


	/**
	 * 下载日志文件
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@Log("下载日志文件")
	@PostMapping("/getlog")
	@RequiresPermissions("system:logfiles")
	ResponseEntity<byte[]> getlogfiles(String fileName) throws IOException {

		String 	downloadFilePath = justdoConfig.getLogfilesPath();

		//String downloadFilePath = "/files/";

			if(StringUtils.isEmpty(fileName)) {
				fileName = DateUtils.formatTimeNow("yyyy-MM-dd")+".log";
			}

		File file = new File(downloadFilePath+File.separator+DateUtils.formatTimeNow("yyyy-MM-dd")+File.separator+fileName);

		HttpHeaders headers = new HttpHeaders();
		String downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
		headers.setContentDispositionFormData("attachment", downloadFileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

			if(file.exists()) {

				headers.setContentDispositionFormData("attachment", downloadFileName);

				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
			}
			else{

				fileName = DateUtils.formatTimeNow("yyyy-MM-dd")+".log";

				File file2 = new File(downloadFilePath+File.separator+DateUtils.formatTimeNow("yyyy-MM-dd")+File.separator+fileName);

				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file2),headers,HttpStatus.CREATED);
			}
	}
}

