package com.justdo.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author justdo
 * @version V1.0
 */

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.timeout}")
    private int timeout;

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

    @Bean
    public JedisPool redisPoolFactory() {
    	Logger.getLogger(getClass()).info("JedisPool注入成功！！");
        Logger.getLogger(getClass()).info("redis地址：" + host + ":" + port);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        jedisPoolConfig.setTestWhileIdle(testWhileIdle);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout,password);

        return jedisPool;
    }

}
