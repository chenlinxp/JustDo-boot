package com.justdo.config;

import com.justdo.common.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @DESCRIPTION
 *
 * @author justdo
 * @create 2017-01-02 23:53
 */
@Configuration
//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	JustdoConfig justdoConfig;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		//默认值为 classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/
		String os = System.getProperty("os.name");
		String uploadPath = justdoConfig.getUploadPath();
		String appUploadPath = justdoConfig.getAppUploadPath();
		String logfiles = justdoConfig.getLogfilesPath();
		if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
			registry.addResourceHandler("/files/**")
					//files/**表示在磁盘files目录下的所有资源会被解析为以下的路径 logfiles
					.addResourceLocations("file:C:"+uploadPath)
					.addResourceLocations("classpath:"+uploadPath);
		} else {  //linux 和mac
			registry.addResourceHandler("/files/**")
					.addResourceLocations("file:"+uploadPath)
					.addResourceLocations("file:"+appUploadPath)
					.addResourceLocations("file:"+logfiles)
					.addResourceLocations("classpath:"+uploadPath)
					.addResourceLocations("classpath:/templates/");
		}
	}


	// 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/api/**");

	}


//	@Bean(name = "multipartResolver")
//	public MultipartResolver multipartResolver(){
//		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//		resolver.setDefaultEncoding("UTF-8");
//		resolver.setResolveLazily(true); //resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
//		resolver.setMaxInMemorySize(200*1024*1024);
//		resolver.setMaxUploadSize(200*1024*1024);//上传文件大小 5M 5*1024*1024
//		return resolver;
//	}
//
//	@Bean
//	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
//		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//		//设置日期格式
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.setDateFormat(CustomDateFormat.instance);
//		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
//		//设置中文编码格式
//		List<MediaType> list = new ArrayList<MediaType>();
//		list.add(MediaType.APPLICATION_JSON_UTF8);
//		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
//		return mappingJackson2HttpMessageConverter;
//	}

//
//	@Bean
//	public DateJacksonConverter dateJacksonConverter() {
//		return new DateJacksonConverter();
//	}
//
//	@Bean
//	public Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean(
//			@Autowired
//					DateJacksonConverter dateJacksonConverter) {
//		Jackson2ObjectMapperFactoryBean jackson2ObjectMapperFactoryBean = new Jackson2ObjectMapperFactoryBean();
//
//		jackson2ObjectMapperFactoryBean.setDeserializers(dateJacksonConverter);
//		return jackson2ObjectMapperFactoryBean;
//	}
//
//	@Bean
//	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
//			@Autowired
//					ObjectMapper objectMapper) {
//		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
//				new MappingJackson2HttpMessageConverter();
//		mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
//		return mappingJackson2HttpMessageConverter;
//	}

}