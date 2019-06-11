package com.justdo.config;


import com.justdo.common.utils.FastJson2JsonRedisSerializer;
import com.justdo.common.utils.RedisUtils;
import com.justdo.common.redis.RedisTemplate;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * RedisConfig  配置
 */
@EnableCaching
@Configuration
@Slf4j
public class RedisConfig {


	@Value("${spring.redis.host}")
	private String hostName = "127.0.0.1";

	@Value("${spring.redis.port}")
	private Integer port = 6379;

	@Value("${spring.redis.password}")
	private String password = "oxhide";

	@Value("${spring.redis.timeout}")
	private Integer timeout = 0;

	@Value("${spring.redis.pool.max-Idle}")
	private Integer maxIdle = 500;

	@Value("${spring.redis.pool.maxTotal}")
	private Integer maxTotal;

	@Value("${spring.redis.pool.maxWaitMillis}")
	private Integer maxWaitMillis;

	@Value("${spring.redis.pool.minEvictableIdleTimeMillis}")
	private Integer minEvictableIdleTimeMillis;

	@Value("${spring.redis.pool.numTestsPerEvictionRun}")
	private Integer numTestsPerEvictionRun;

	@Value("${spring.redis.pool.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;

	@Value("${spring.redis.pool.testOnBorrow}")
	private boolean testOnBorrow = false;

	@Value("${spring.redis.pool.testOnReturn}")
	private boolean testOnReturn = true;

	@Value("${spring.redis.pool.testWhileIdle}")
	private boolean testWhileIdle = true;

	/**
	 * @return: org.springframework.data.redis.connection.jedis.JedisConnectionFactory
	 * @Description: Jedis配置
	 */
//	@Bean
//	public JedisConnectionFactory JedisConnectionFactory(){
//		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration ();
//		redisStandaloneConfiguration.setHostName(hostName);
//		redisStandaloneConfiguration.setPort(port);
//		//由于我们使用了动态配置库,所以此处省略
//		//redisStandaloneConfiguration.setDatabase(database);
//		redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
//		JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
//		jedisClientConfiguration.connectTimeout(Duration.ofMillis(timeout));
//		JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,
//				jedisClientConfiguration.build());
//		return factory;
//	}

	/**
	 * @return: com.springboot.demo.base.utils.RedisTemplate
	 * @Description: 实例化 RedisTemplate 对象
	 */
	@Bean
	public RedisTemplate functionDomainRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		//log.info("RedisTemplate实例化成功！");
		RedisTemplate redisTemplate = new RedisTemplate();
		initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
		return redisTemplate;
	}

	/**
	 * @return: org.springframework.data.redis.serializer.RedisSerializer
	 * @Description: 引入自定义序列化
	 */
	@Bean
	public RedisSerializer fastJson2JsonRedisSerializer() {
		return new FastJson2JsonRedisSerializer<Object>(Object.class);
	}

	/**
	 * @param: [redisTemplate, factory]
	 * @return: void
	 * @Description: 设置数据存入 redis 的序列化方式,并开启事务
	 */
	private void initDomainRedisTemplate(RedisTemplate redisTemplate, RedisConnectionFactory factory) {
		//如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
		// 开启事务
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(factory);
	}

	/**
	 * @param: [redisTemplate]
	 * @return: com.springboot.demo.base.utils.RedisUtil
	 * @Description: 注入封装RedisTemplate
	 */
	@Bean(name = "redisUtils")
	public RedisUtils redisUtils(RedisTemplate redisTemplate) {
		//log.info("RedisUtil注入成功！");
		RedisUtils redisUtils = new RedisUtils();
		redisUtils.setRedisTemplate(redisTemplate);
		return redisUtils;
	}

}

