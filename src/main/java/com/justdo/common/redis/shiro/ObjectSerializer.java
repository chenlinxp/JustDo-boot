package com.justdo.common.redis.shiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午5:14
 */
public class ObjectSerializer implements RedisSerializer<Object> {
	private static Logger logger = LoggerFactory.getLogger(ObjectSerializer.class);
	public static final int BYTE_ARRAY_OUTPUT_STREAM_SIZE = 128;

	public ObjectSerializer() {
	}

	@Override
	public byte[] serialize(Object object) throws SerializationException {
		byte[] result = new byte[0];
		if(object == null) {
			return result;
		} else {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			if(!(object instanceof Serializable)) {
				throw new SerializationException("requires a Serializable payload but received an object of type [" + object.getClass().getName() + "]");
			} else {
				try {
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
					objectOutputStream.writeObject(object);
					objectOutputStream.flush();
					result = byteStream.toByteArray();
					return result;
				} catch (IOException var5) {
					throw new SerializationException("serialize error, object=" + object, var5);
				}
			}
		}
	}
	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		Object result = null;
		if(bytes != null && bytes.length != 0) {
			try {
				ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
				ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
				result = objectInputStream.readObject();
				return result;
			} catch (IOException var5) {
				throw new SerializationException("deserialize error", var5);
			} catch (ClassNotFoundException var6) {
				throw new SerializationException("deserialize error", var6);
			}
		} else {
			return result;
		}
	}
}

