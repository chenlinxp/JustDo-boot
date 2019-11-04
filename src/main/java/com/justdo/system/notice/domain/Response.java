package com.justdo.system.notice.domain;

/**
 * 通知通告
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */
public class Response {
	public void setResponseMessage(Message responseMessage) {
		this.responseMessage = responseMessage;
	}

	private Message responseMessage;

	public Response(Message responseMessage) {

		this.responseMessage = responseMessage;
	}

	public Message getResponseMessage() {
		return responseMessage;
	}
}
