package com.justdo.common.redis.shiro;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.HashSet;
import java.util.Set;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午2:53
 */

public abstract class BaseRedisManager implements IRedisManager {
	protected static final int DEFAULT_EXPIRE = -1;
	protected static final int DEFAULT_COUNT = 100;
	private int count = 100;
	private JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

	public BaseRedisManager() {
	}

	protected abstract Jedis getJedis();

	@Override
	public byte[] get(byte[] key) {
		if(key == null) {
			return null;
		} else {
			byte[] value = null;
			Jedis jedis = this.getJedis();
			try {
				value = jedis.get(key);
			} finally {
				jedis.close();
			}

			return value;
		}
	}

	/**
	 * set
	 *
	 * @param key
	 * @param value
	 * @return
	 */

	@Override
	public byte[] set(byte[] key, byte[] value, int exipreTime) {
		if(key == null) {
			return null;
		} else {
			Jedis jedis = this.getJedis();

			try {
				jedis.set(key, value);
				if(exipreTime >= 0) {
					jedis.expire(key, exipreTime);
				}
			} finally {
				jedis.close();
			}

			return value;
		}
	}

	@Override
	public void del(byte[] key) {
		if(key != null) {
			Jedis jedis = this.getJedis();

			try {
				jedis.del(key);
			} finally {
				jedis.close();
			}

		}
	}

	@Override
	public Long dbSize(byte[] pattern) {
		long dbSize = 0L;
		Jedis jedis = this.getJedis();

		try {
			ScanParams params = new ScanParams();
			params.count(Integer.valueOf(this.count));
			params.match(pattern);
			byte[] cursor = ScanParams.SCAN_POINTER_START_BINARY;

			ScanResult scanResult;
			do {
				scanResult = jedis.scan(cursor, params);
				++dbSize;
				cursor = scanResult.getCursorAsBytes();
			} while(scanResult.getStringCursor().compareTo(ScanParams.SCAN_POINTER_START) > 0);
		} finally {
			jedis.close();
		}

		return Long.valueOf(dbSize);
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		Set<byte[]> keys = new HashSet();
		Jedis jedis = this.getJedis();

		try {
			ScanParams params = new ScanParams();
			params.count(Integer.valueOf(this.count));
			params.match(pattern);
			byte[] cursor = ScanParams.SCAN_POINTER_START_BINARY;

			ScanResult scanResult;
			do {
				scanResult = jedis.scan(cursor, params);
				keys.addAll(scanResult.getResult());
				cursor = scanResult.getCursorAsBytes();
			} while(scanResult.getStringCursor().compareTo(ScanParams.SCAN_POINTER_START) > 0);
		} finally {
			jedis.close();
		}

		return keys;
	}


	public void flushDB() {
		Jedis jedis = getJedis();
		try {
			jedis.flushDB();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * size
	 */

	public Long dbSize() {
		Long dbSize = 0L;
		Jedis jedis = getJedis();
		try {
			dbSize = jedis.dbSize();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return dbSize;
	}

    @Override
	public Set<byte[]> keys(String pattern) {
		Set<byte[]> keys = null;
		Jedis jedis = getJedis();
		try {
			keys = jedis.keys(pattern.getBytes());
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
		return keys;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return this.jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}
}
