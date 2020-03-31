package com.justdo.config;

import com.justdo.common.interceptor.MessageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * 通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,
 * 此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:36:21
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * endPoint 注册协议节点,并映射指定的URl
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        //endPoint 注册协议节点,并映射指定的URl
        //注册一个名字为"endpointChat" 的endpoint,并指定 SockJS协议。   点对点-用
//        registry.addEndpoint("/justdo-websocket").setAllowedOrigins("*").withSockJS();
//
        registry.addEndpoint("/justdo-websocket")
                .addInterceptors(new HttpSessionHandshakeInterceptor())
                .setAllowedOrigins("*")
                .withSockJS();

    }

    /**
     * 配置消息代理(message broker)
     * <p>应用程序以/app为前缀，
     * 代理目的地以/topic、/queue为前缀
     * 客户端订阅点对点地址以 /justdo为前缀
     * </p>
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //订阅Broker名称
        //配置消息代理(message broker)
        //点对点式增加一个/queue 消息代理
        //在客户端订阅地址、服务端发送–>客户端时的地址前缀
        registry.enableSimpleBroker("/queue", "/topic");
        //以"/app”打头的消息都将会路由到带有@MessageMapping注解的方法中，而不会发布到代理队列或主题中。
        //在客户端发送–>服务端时的地址前缀
        //全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app");
        //设置前缀  默认是user 可以修改  点对点时使用
        //可以不设置，客户端订阅点对点地址时的地址前缀，默认不设置时是/user/
        registry.setUserDestinationPrefix("/justdo/");

    }

    /**
     * 配置客户端入站通道拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors(new MessageInterceptor());
    }


    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {

        registration.setMessageSizeLimit(500 * 1024 * 1024);
        registration.setSendBufferSizeLimit(1024 * 1024 * 1024);
        registration.setSendTimeLimit(200000);
    }

}
