package com.justdo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @DESCRIPTION
 *
 * @author justdo
 * @create 2017-01-02 23:53
 */
@Component
class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	JustdoConfig justdoConfig;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		//默认值为 classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/
		String os = System.getProperty("os.name");
		String uploadPath = justdoConfig.getUploadPath();
		if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
			registry.addResourceHandler("/files/**")
					// /files/**表示在磁盘files目录下的所有资源会被解析为以下的路径
					.addResourceLocations("file:C:/"+uploadPath)
					.addResourceLocations("classpath:/"+uploadPath);
		} else {  //linux 和mac
			registry.addResourceHandler("/files/**")
					.addResourceLocations("file:/"+uploadPath)
					.addResourceLocations("classpath:/"+uploadPath);
		}
	}




	// 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
	@Override
	public void addInterceptors(InterceptorRegistry registry) {


	}
}