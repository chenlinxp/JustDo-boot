package com.justdo.common.domain;


import java.security.Principal;

/**
 * Created by chenlin on 2019/4/27.
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