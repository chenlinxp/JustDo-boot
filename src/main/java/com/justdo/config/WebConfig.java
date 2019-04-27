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
	registry.addResourceHandler("/files/**")
			//.addResourceLocations("files:/Users/chenlin/Documents/GitHub/JustDo-boot/target/classes/"+justdoConfig.getUploadPath());
			.addResourceLocations("classpath:/"+justdoConfig.getUploadPath());
	}
	// 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
	@Override
	public void addInterceptors(InterceptorRegistry registry) {


	}
}