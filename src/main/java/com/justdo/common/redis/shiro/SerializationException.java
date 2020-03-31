package com.justdo.common.redis.shiro;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午5:05
 */
public class SerializationException extends Exception {

	public SerializationException(String msg) {
		super(msg);
	}

	public SerializationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
