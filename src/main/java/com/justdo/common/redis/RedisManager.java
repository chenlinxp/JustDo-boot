package com.justdo.common.redis;

import com.justdo.common.redis.shiro.BaseRedisManager;
import com.justdo.common.redis.shiro.IRedisManager;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author justdo
 * @version V1.0
 */
public class RedisManager extends BaseRedisManager implements IRedisManager{

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
    private boolean testOnBorrow = true;

    @Value("${spring.redis.pool.testOnReturn}")
    private boolean testOnReturn = true;

    @Value("${spring.redis.pool.testWhileIdle}")
    private boolean testWhileIdle = true;

    @Value("${spring.redis.pool.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis = 6000;

    private int database = 0;

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
    @Override
     protected   Jedis getJedis() {
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
