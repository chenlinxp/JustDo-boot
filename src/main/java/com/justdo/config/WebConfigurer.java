package com.justdo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @DESCRIPTION
 *
 * @author justdo
 * @create 2017-01-02 23:53
 */
@Component
class WebConfigurer extends WebMvcConfigurerAdapter {
	@Autowired
	JustdoConfig justdoConfig;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/files/**")
			//.addResourceLocations("files:/Users/chenlin/Documents/GitHub/JustDo-boot/target/classes/"+justdoConfig.getUploadPath());
			.addResourceLocations("classpath:/"+justdoConfig.getUploadPath());
	}

}