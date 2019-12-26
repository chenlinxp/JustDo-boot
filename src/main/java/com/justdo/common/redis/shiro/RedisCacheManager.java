package com.justdo.common.redis.shiro;

/**
 * @author justdo
 * @version V1.0
 */

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class RedisCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
    private RedisSerializer keySerializer = new StringSerializer();
    private RedisSerializer valueSerializer = new ObjectSerializer();
    private static final int DEFAULT_EXPIRE = 1800;
    private int expire = 1800;
    private IRedisManager redisManager;
    public static final String DEFAULT_CACHE_KEY_PREFIX = "shiro:cache:";

    public static final String DEFAULT_PRINCIPAL_ID_FIELD_NAME = "authCacheKey or id";
    private String principalIdFieldName = "authCacheKey or id";
    /**
     * The Redis key prefix for caches
     */
    private String keyPrefix = "shiro:cache:";
    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }


    public RedisCacheManager() {
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("get cache, name=" + name);
        Cache cache = (Cache)this.caches.get(name);
        if(cache == null) {
            cache = new RedisCache(this.redisManager, this.keySerializer, this.valueSerializer, this.keyPrefix, this.expire, this.principalIdFieldName);
            this.caches.put(name, cache);
        }

        return (Cache)cache;
    }
    public IRedisManager getRedisManager() {
        return this.redisManager;
    }

    public void setRedisManager(IRedisManager redisManager) {
        this.redisManager = redisManager;
    }

    public RedisSerializer getKeySerializer() {
        return this.keySerializer;
    }

    public void setKeySerializer(RedisSerializer keySerializer) {
        this.keySerializer = keySerializer;
    }

    public RedisSerializer getValueSerializer() {
        return this.valueSerializer;
    }

    public void setValueSerializer(RedisSerializer valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public int getExpire() {
        return this.expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getPrincipalIdFieldName() {
        return this.principalIdFieldName;
    }

    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }
}
