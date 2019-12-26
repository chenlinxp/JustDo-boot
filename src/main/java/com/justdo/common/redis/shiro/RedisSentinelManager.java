package com.justdo.common.redis.shiro;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午3:13
 */
public class RedisSentinelManager extends BaseRedisManager implements IRedisManager {
	private static final String DEFAULT_HOST = "127.0.0.1:26379,127.0.0.1:26380,127.0.0.1:26381";
	private String host = "127.0.0.1:26379,127.0.0.1:26380,127.0.0.1:26381";
	private static final String DEFAULT_MASTER_NAME = "mymaster";
	private String masterName = "mymaster";
	private int timeout = 2000;
	private int soTimeout = 2000;
	private String password;
	private int database = 0;
	private JedisSentinelPool jedisPool;

	public RedisSentinelManager() {
	}

	@Override
	protected Jedis getJedis() {
		if (this.jedisPool == null) {
			this.init();
		}

		return this.jedisPool.getResource();
	}

	private void init() {
		synchronized (this) {
			if (this.jedisPool == null) {
				String[] sentinelHosts = this.host.split(",\\s*");
				Set<String> sentinels = new HashSet();
				Collections.addAll(sentinels, sentinelHosts);
				this.jedisPool = new JedisSentinelPool(this.masterName, sentinels, this.getJedisPoolConfig(), this.timeout, this.soTimeout, this.password, this.database);
			}

		}
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getTimeout() {
		return this.timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDatabase() {
		return this.database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getMasterName() {
		return this.masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public int getSoTimeout() {
		return this.soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
}
