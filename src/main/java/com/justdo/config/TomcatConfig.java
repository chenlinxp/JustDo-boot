package com.justdo.config;

import org.springframework.context.annotation.Configuration;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/5/7 下午4:58
 */
@Configuration
public class TomcatConfig {
//	@Bean
//						TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() {
//			TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory(){
//				@Override
//				protected void postProcessContext(Context context) {
//					SecurityConstraint constraint = new SecurityConstraint();
//					constraint.setUserConstraint("CONFIDENTIAL");
//					SecurityCollection collection = new SecurityCollection();
//					collection.addPattern("/*");
//					constraint.addCollection(collection);
//					context.addConstraint(constraint);
//				}
//			};
//			factory.addAdditionalTomcatConnectors(createTomcatConnector());
//			return factory;
//		}
//
//	private Connector createTomcatConnector() {
//		Connector connector = new
//				Connector("org.apache.coyote.http11.Http11NioProtocol");
//		connector.setScheme("http");
//		connector.setPort(8088);
//		connector.setSecure(false);
//		connector.setRedirectPort(443);
//		return connector;
//	}
}

