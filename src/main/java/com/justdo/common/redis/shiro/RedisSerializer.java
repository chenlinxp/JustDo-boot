package com.justdo.common.redis.shiro;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午5:05
 */
public interface RedisSerializer<T> {

	byte[] serialize(T var1) throws SerializationException;

	T deserialize(byte[] var1) throws SerializationException;
}