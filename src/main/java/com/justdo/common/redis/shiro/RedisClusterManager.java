package com.justdo.common.redis.shiro;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午3:06
 */
public class RedisClusterManager implements IRedisManager {
	private static final int DEFAULT_COUNT = 100;
	private static final int DEFAULT_MAX_ATTEMPTS = 3;
	private static final String DEFAULT_HOST = "127.0.0.1:7000,127.0.0.1:7001,127.0.0.1:7002";
	private String host = "127.0.0.1:7000,127.0.0.1:7001,127.0.0.1:7002";
	private int timeout = 2000;
	private int soTimeout = 2000;
	private String password;
	private int database = 0;
	private int count = 100;
	private int maxAttempts = 3;
	private JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
	private volatile JedisCluster jedisCluster = null;

	public RedisClusterManager() {
	}

	private void init() {
		synchronized (this) {
			if (this.jedisCluster == null) {
				this.jedisCluster = new JedisCluster(this.getHostAndPortSet(), this.timeout, this.soTimeout, this.maxAttempts, this.password, this.getJedisPoolConfig());
			}

		}
	}

	private Set<HostAndPort> getHostAndPortSet() {
		String[] hostAndPortArr = this.host.split(",");
		Set<HostAndPort> hostAndPorts = new HashSet();
		String[] arr$ = hostAndPortArr;
		int len$ = hostAndPortArr.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			String hostAndPortStr = arr$[i$];
			String[] hostAndPort = hostAndPortStr.split(":");
			hostAndPorts.add(new HostAndPort(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
		}

		return hostAndPorts;
	}

	protected JedisCluster getJedisCluster() {
		if (this.jedisCluster == null) {
			this.init();
		}

		return this.jedisCluster;
	}

    @Override
	public byte[] get(byte[] key) {
		return key == null ? null : this.getJedisCluster().get(key);
	}

	@Override
	public byte[] set(byte[] key, byte[] value, int expireTime) {
		if (key == null) {
			return null;
		} else {
			this.getJedisCluster().set(key, value);
			if (expireTime >= 0) {
				this.getJedisCluster().expire(key, expireTime);
			}

			return value;
		}
	}

	@Override
	public void del(byte[] key) {
		if (key != null) {
			this.getJedisCluster().del(key);
		}
	}

	@Override
	public Long dbSize(byte[] pattern) {
		Long dbSize = Long.valueOf(0L);
		Map<String, JedisPool> clusterNodes = this.getJedisCluster().getClusterNodes();
		Iterator nodeIt = clusterNodes.entrySet().iterator();

		while (nodeIt.hasNext()) {
			Map.Entry<String, JedisPool> node = (Map.Entry) nodeIt.next();
			long nodeDbSize = this.getDbSizeFromClusterNode((JedisPool) node.getValue(), pattern);
			if (nodeDbSize != 0L) {
				dbSize = Long.valueOf(dbSize.longValue() + nodeDbSize);
			}
		}

		return dbSize;
	}

	@Override
	public Set<byte[]> keys(byte[] pattern) {
		Set<byte[]> keys = new HashSet();
		Map<String, JedisPool> clusterNodes = this.getJedisCluster().getClusterNodes();
		Iterator nodeIt = clusterNodes.entrySet().iterator();

		while (nodeIt.hasNext()) {
			Map.Entry<String, JedisPool> node = (Map.Entry) nodeIt.next();
			Set<byte[]> nodeKeys = this.getKeysFromClusterNode((JedisPool) node.getValue(), pattern);
			if (nodeKeys != null && nodeKeys.size() != 0) {
				keys.addAll(nodeKeys);
			}
		}

		return keys;
	}

	private Set<byte[]> getKeysFromClusterNode(JedisPool jedisPool, byte[] pattern) {
		Set<byte[]> keys = new HashSet();
		Jedis jedis = jedisPool.getResource();

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
			} while (scanResult.getStringCursor().compareTo(ScanParams.SCAN_POINTER_START) > 0);
		} finally {
			jedis.close();
		}

		return keys;
	}

	private long getDbSizeFromClusterNode(JedisPool jedisPool, byte[] pattern) {
		long dbSize = 0L;
		Jedis jedis = jedisPool.getResource();

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
			} while (scanResult.getStringCursor().compareTo(ScanParams.SCAN_POINTER_START) > 0);
		} finally {
			jedis.close();
		}

		return dbSize;
	}

	@Override
	public Set<byte[]> keys(String pattern) {
		Set<byte[]> keys = null;

			keys = this.getJedisCluster().hkeys(pattern.getBytes());

		return keys;
	}

	public int getMaxAttempts() {
		return this.maxAttempts;
	}

	public void setMaxAttempts(int maxAttempts) {
		this.maxAttempts = maxAttempts;
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

	public int getSoTimeout() {
		return this.soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
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

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	public JedisPoolConfig getJedisPoolConfig() {
		return this.jedisPoolConfig;
	}

	public void setJedisPoolConfig(JedisPoolConfig jedisPoolConfig) {
		this.jedisPoolConfig = jedisPoolConfig;
	}
}
