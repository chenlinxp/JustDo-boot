package com.justdo.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/3/21 下午10:05
 */

@Configuration
public class MultipartConfig {

//	@Value("${spring.http.maxUploadSize}")
//	private String maxUploadSize;
//
//	@Value("${spring.http.maxInMemorySize}")
//	private String maxInMemorySize;


	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(200*1024*1024);
		multipartResolver.setMaxInMemorySize(200*1024*1024);
		return multipartResolver;
	}
}