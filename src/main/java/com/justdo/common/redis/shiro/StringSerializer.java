package com.justdo.common.redis.shiro;

import java.io.UnsupportedEncodingException;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午5:08
 */
public class StringSerializer implements RedisSerializer<String> {
	private static final String DEFAULT_CHARSET = "UTF-8";
	private String charset = "UTF-8";

	public StringSerializer() {
	}

	@Override
	public byte[] serialize(String s) throws SerializationException {
		try {
			return s == null?null:s.getBytes(this.charset);
		} catch (UnsupportedEncodingException var3) {
			throw new SerializationException("serialize error, string=" + s, var3);
		}
	}

	@Override
	public String deserialize(byte[] bytes) throws SerializationException {
		try {
			return bytes == null?null:new String(bytes, this.charset);
		} catch (UnsupportedEncodingException var3) {
			throw new SerializationException("deserialize error", var3);
		}
	}

	public String getCharset() {
		return this.charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}

