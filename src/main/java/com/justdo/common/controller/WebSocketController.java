package com.justdo.common.controller;


import com.justdo.system.employee.domain.SimpleEmployeeDO;
import com.justdo.system.employee.service.ESessionService;
import com.justdo.system.notice.domain.Message;
import com.justdo.system.notice.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket控制器
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:36:21
 */
@MessageMapping("/msg")
@RequestMapping("/msg")
@Controller
public class WebSocketController {
	@Autowired
	SimpMessagingTemplate template;

	@Autowired
	private SimpMessageSendingOperations simpMessageSendingOperations;

	@Autowired
	ESessionService esessionService;



	@MessageMapping(value = "/welcome") // 浏览器发送请求通过@messageMapping 映射/welcome 这个地址。
	@SendTo("/topic/greetings") // 服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
	public Response welcome(@RequestBody Message message) throws Exception {
		//Thread.sleep(1000);
		return new Response(message);
	}

	/**
	 * 同样的发送消息   只不过是ws版本  http请求不能访问
	 * 根据用户key发送消息
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@MessageMapping(value = "/sendPointToPoint")
	public void sendPointToPoint(Message message) throws Exception {

		Map<String,String> params = new HashMap();
		params.put("test","test");
		//这里没做校验
		String sessionId="";
		template.convertAndSendToUser(sessionId,"/topic/greetings",message,createHeaders(sessionId));
	}


	private MessageHeaders createHeaders(String sessionId) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
		headerAccessor.setSessionId(sessionId);
		headerAccessor.setLeaveMutable(true);
		return headerAccessor.getMessageHeaders();
	}

	/**
	 * 表示服务端可以接收客户端通过主题“/app/hello”发送过来的消息，客户端需要在主题"/topic/hello"上监听并接收服务端发回的消息
	 * @param topic
	 * @param headers
	 */
	@MessageMapping("/greeting")
	@SendTo("/topic/greetings")
	public void greeting(@Header("atytopic") String topic, @Headers Map<String, Object> headers) {
		System.out.println("connected successfully....");
		System.out.println(topic);
		System.out.println(headers);
	}

	/**
	 * 这里用的是@SendToUser，这就是发送给单一客户端的标志。本例中，
	 * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。
	 * @return
	 */
	@MessageMapping("/message")
	@SendToUser("/queue/message")
	public Message message() {
		System.out.println("this is the @SubscribeMapping('/marco')");

		return new Message("","new message","I am a msg from SubscribeMapping('/macro').");
	}

	/**
	 * 用户广播
	 * 发送消息广播  用于内部发送使用
	 * @param message
	 * @return
	 */
	@GetMapping("/sendBroadcast")
	@ResponseBody
	public Message sendBroadcast(@RequestBody Message message){

		for (SimpleEmployeeDO simpleEmployeeDO : esessionService.listOnlineEmployee()) {
			String employeeId = simpleEmployeeDO.getId();
			template.convertAndSendToUser(employeeId,"/queue/message",message);
		}

		template.convertAndSend("/topic/greetings",new Response(message));
		return message;
	}

	/**
	 * 测试对指定用户发送消息方法
	 * @return
	 */
	@RequestMapping(path = "/send", method = RequestMethod.GET)
	public Message send() {
		simpMessageSendingOperations.convertAndSendToUser("1", "/queue/message", new Message("","new message","I am a msg from SubscribeMapping('/macro')."));
		return new Message("ww","new message","I am a msg from SubscribeMapping('/macro').");
	}

}