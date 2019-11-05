package com.justdo.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Slowlog;

import java.util.List;
import java.util.Set;

@Component
public class RedisUtils {

	@Autowired
	JedisPool jedisPool;

	// 获取redis 服务器信息
	public String getRedisInfo() {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			Client client = jedis.getClient();
			client.info();
			String info = client.getBulkReply();
			return info;
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 获取日志列表
	public List<Slowlog> getLogs(long entries) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			List<Slowlog> logList = jedis.slowlogGet(entries);
			return logList;
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 获取日志条数
	public Long getLogsLen() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			long logLen = jedis.slowlogLen();
			return logLen;
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 清空日志
	public String logEmpty() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.slowlogReset();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	// 获取占用内存大小
	public Long dbSize() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			// TODO 配置redis服务信息
			Client client = jedis.getClient();
			client.dbSize();
			return client.getIntegerReply();
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	/**
	 * get value from redis
	 *
	 * @param key
	 * @return
	 */
	public byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = jedisPool.getResource();
		try {
			value = jedis.get(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	/**
	 * set
	 *
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public byte[] set(byte[] key, byte[] value, int expire) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.set(key, value);
			if (expire != 0) {
				jedis.expire(key, expire);
			}
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return value;
	}

	/**
	 * del
	 *
	 * @param key
	 */
	public void del(byte[] key) {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.del(key);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * flush
	 */
	public void flushDB() {
		Jedis jedis = jedisPool.getResource();
		try {
			jedis.flushDB();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * keys
	 *
	 * @param pattern
	 * @return
	 */
	public Set<byte[]> keys(String pattern) {
		Set<byte[]> keys = null;
		Jedis jedis = jedisPool.getResource();
		try {
			keys = jedis.keys(pattern.getBytes());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return keys;
	}
}
