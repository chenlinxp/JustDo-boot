package com.justdo.common.interceptor;

import com.justdo.common.domain.ChatUser;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.LinkedList;
import java.util.Map;

/**
 * 自定义消息拦截器
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:36:21
 */
public class MessageInterceptor extends ChannelInterceptorAdapter {

	/**
	* 获取包含在stomp中的用户信息
	*/
	@SuppressWarnings("rawtypes")
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {

	StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
	if (StompCommand.CONNECT.equals(accessor.getCommand())) {
		Object raw = message.getHeaders().get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
		if (raw instanceof Map) {
			Object name = ((Map) raw).get("name");
			if (name instanceof LinkedList) {
				// 设置当前访问器的认证用户
				accessor.setUser(new ChatUser(((LinkedList) name).get(0).toString()));
				}
			}
		}else if (StompCommand.SEND.equals(accessor.getCommand())) {
		//发送数据

	   }
		return message;
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

	}

	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {

	}

	@Override
	public boolean preReceive(MessageChannel channel) {
		return false;
	}

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		return null;
	}

	@Override
	public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {

	}
}
