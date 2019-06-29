package com.justdo.common.redis;

import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * @author justdo
 * @version V1.0
 */
public class RedisManager {

    @Value("${spring.redis.host}")
    private String host = "127.0.0.1";

    @Value("${spring.redis.port}")
    private int port = 6379;
    // 0 - never expire
    @Value("${spring.redis.expire}")
    private int expire = 0;
    //timeout for jedis try to connect to redis server, not expire time! In milliseconds
    @Value("${spring.redis.timeout}")
    private int timeout = 0;

    @Value("${spring.redis.password}")
    private String password = "oxhide";

    @Value("${spring.redis.pool.min-Idle}")
    private int minIdle = 300;

    @Value("${spring.redis.pool.max-active}")
    private int maxactive = 5000;

    @Value("${spring.redis.pool.max-Idle}")
    private int maxIdle = 500;

    @Value("${spring.redis.pool.max-Wait}")
    private int maxWait = -1;

    @Value("${spring.redis.pool.testOnBorrow}")
    private boolean testOnBorrow = false;

    @Value("${spring.redis.pool.testOnReturn}")
    private boolean testOnReturn = true;

    @Value("${spring.redis.pool.testWhileIdle}")
    private boolean testWhileIdle = true;

    @Value("${spring.redis.pool.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis = 6000;

    private static JedisPool jedisPool = null;

    public RedisManager() {



    }

    /**
     * 初始化方法
     */
    public void init() {

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxactive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setTestOnBorrow(testOnBorrow);
        poolConfig.setTestOnReturn(testOnReturn);
        poolConfig.setTestWhileIdle(testWhileIdle);
        poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        if (jedisPool == null) {
            if (password != null && !"".equals(password)) {
                jedisPool = new JedisPool(poolConfig, host, port, timeout, password);
            } else if (timeout != 0) {
                jedisPool = new JedisPool(poolConfig, host, port, timeout);
            } else {
                jedisPool = new JedisPool(poolConfig, host, port);
            }

        }
    }

    /**
     * 获取连接池中的redis实例
     * @return obj
     */
    private  Jedis getJedis() {
        if (jedisPool == null) {
            try {
                init();
            } catch (Exception e) {
                throw new CacheException("缓存连接池初始化异常", e);
            }
        }
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
    /**
     * get value from redis
     *
     * @param key
     * @return
     */
    public byte[] get(byte[] key) {
        byte[] value = null;
        Jedis jedis = getJedis();
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
     * @return
     */
    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            if (this.expire != 0) {
                jedis.expire(key, this.expire);
            }
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
        Jedis jedis = getJedis();
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
        Jedis jedis = getJedis();
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

    /**
     * keys
     *
     * @param pattern
     * @return
     */
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
