package com.justdo.common.domain;


import java.security.Principal;

/**
 * stomp中的用户信息
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:36:21
 */

public final class ChatUser implements Principal {

	private final String name;

	public ChatUser(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
}