package com.justdo.system.notice.domain;

import com.justdo.common.domain.BaseBean;

/**
 * 消息通知
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-26 18:32:20
 */
public class Message extends BaseBean {

	private String messageId;

	private String messageTitle;

	private String messageContent;

	public Message(String messageId, String messageTitle, String messageContent) {
		this.messageId = messageId;
		this.messageTitle = messageTitle;
		this.messageContent = messageContent;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}


}
