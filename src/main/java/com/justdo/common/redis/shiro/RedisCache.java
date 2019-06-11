package com.justdo.common.redis.shiro;

/**
 * @author justdo
 * @version V1.0
 */

import com.justdo.common.utils.SerializeUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

public class RedisCache<K,V> implements Cache<K,V> {

    private RedisTemplate redisTemplate;

    private String prefix = "shiro_redis:";

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Constructs a cache instance with the specified RedisTemplate.
     * @param redisTemplate The cache manager instance
     */
    public RedisCache(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    /**
     * Constructs a cache instance with the specified
     * RedisTemplate and using a custom key prefix.
     * @param redisTemplate The cache manager instance
     * @param prefix The Redis key prefix
    */
    public RedisCache(RedisTemplate redisTemplate,String prefix){
        this(redisTemplate);
        this.prefix = prefix;
    }

    @Override
    public V get(K k) throws CacheException {
        logger.debug("根据key从Redis中获取对象 key [" + k + "]");
        if (k == null) {
            return null;
        }
        byte[] bytes = getBytesKey(k);
        return (V)redisTemplate.opsForValue().get(bytes);

    }

    @Override
    public V put(K k, V v) throws CacheException {
        logger.debug("根据key从存储 key [" + k + "]");
        if (k== null || v == null) {
            return null;
        }

        byte[] bytes = getBytesKey(k);
        redisTemplate.opsForValue().set(bytes, v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        logger.debug("从redis中删除 key [" + k + "]");
        if(k==null){
            return null;
        }
        byte[] bytes =getBytesKey(k);
        V v = (V)redisTemplate.opsForValue().get(bytes);
        redisTemplate.delete(bytes);
        return v;
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("从redis中删除所有元素");
        redisTemplate.getConnectionFactory().getConnection().flushDb();

    }

    @Override
    public int size() {
        logger.debug("从redis中获取所有元素的数量");
        return redisTemplate.getConnectionFactory().getConnection().dbSize().intValue();
    }

    @Override
    public Set<K> keys() {
        byte[] bytes = (prefix+"*").getBytes();
        Set<byte[]> keys = redisTemplate.keys(bytes);
        Set<K> sets = new HashSet<>();
        for (byte[] key:keys) {
            sets.add((K)key);
        }
        return sets;
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = keys();
        List<V> values = new ArrayList<>(keys.size());
        for(K k :keys){
            values.add(get(k));
        }
        return values;
    }

    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getBytesKey(K key){
        if(key instanceof String){
            String prekey = this.prefix + key;
            return prekey.getBytes();
        }else {
            return SerializeUtils.serialize(key);
        }
    }

}

