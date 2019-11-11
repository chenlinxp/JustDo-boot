package com.justdo.config;

import com.justdo.common.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
public class WebConfig extends WebMvcConfigurerAdapter {
	@Autowired
	JustdoConfig justdoConfig;
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		//默认值为 classpath:/META-INF/resources/,classpath:/resources/,classpath:/static/,classpath:/public/
		String os = System.getProperty("os.name");
		String uploadPath = justdoConfig.getUploadPath();
		if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
			registry.addResourceHandler("/files/**")
					//files/**表示在磁盘files目录下的所有资源会被解析为以下的路径
					.addResourceLocations("file:C:/"+uploadPath)
					.addResourceLocations("classpath:/"+uploadPath);
		} else {  //linux 和mac
			registry.addResourceHandler("/files/**")
					.addResourceLocations("file:/"+uploadPath)
					.addResourceLocations("classpath:/"+uploadPath)
					.addResourceLocations("classpath:/templates/");
		}
	}


	// 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		registry.addInterceptor(new MyInterceptor()).addPathPatterns("/api/*/**");

	}
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
//	}@Target(ElementType.TYPE)
//	@Retention(RetentionPolicy.RUNTIME)
//	@Documented


//	@Target(ElementType.TYPE)
//	@Retention(RetentionPolicy.RUNTIME)
//	@Documented
//	@Component
}