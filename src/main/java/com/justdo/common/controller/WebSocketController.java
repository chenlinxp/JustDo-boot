package com.justdo.common.controller;


import com.justdo.system.employee.domain.EmployeeDO;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import com.justdo.system.employee.service.ESessionService;
import com.justdo.system.notice.domain.Message;
import com.justdo.system.notice.domain.Response;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WebSocketController {
	@Autowired
	SimpMessagingTemplate template;

	@Autowired
	ESessionService esessionService;

//	@Autowired
//	WelcomeTask welcomeTask;


	@MessageMapping("/welcome") // 浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
	@SendTo("/topic/getResponse") // 服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
	public Response say(Message message) throws Exception {
		Thread.sleep(1000);
		return new Response("Welcome, " + message.getMessageTitle()+ "!");
	}

	/**
	 * 用户广播
	 * 发送消息广播  用于内部发送使用
	 * @param message
	 * @return
	 */
	@GetMapping("/msg/sendBroadcast")
	@ResponseBody
	public Message sendBroadcast(Message message){

		for (SimpleEmployeeDO simpleEmployeeDO : esessionService.listOnlineEmployee()) {
			String employeeId = simpleEmployeeDO.getEmployeeId();
			template.convertAndSendToUser(employeeId,"/topic/getResponse",message);
		}
		return message;
	}


	/**
	 * 同样的发送消息   只不过是ws版本  http请求不能访问
	 * 根据用户key发送消息
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping("/msg/sendPointToPoint")
	public void sendPointToPoint(Message message) throws Exception {
		Map<String,String> params = new HashMap();
		params.put("test","test");
		//这里没做校验
		String sessionId="";
		template.convertAndSendToUser(sessionId,"/topic/getResponse",message,createHeaders(sessionId));
	}
	private MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}

}