package com.justdo.common.redis.shiro;

/**
 * @author justdo
 * @version V1.0
 */

import com.justdo.common.exception.PrincipalIdNullException;
import com.justdo.common.exception.PrincipalInstanceException;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class RedisCache<K, V> implements Cache<K, V> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private RedisSerializer keySerializer = new StringSerializer();
    private RedisSerializer valueSerializer = new ObjectSerializer();
    /**
     * The wrapped Jedis instance.
     */
    private IRedisManager redisManager;
    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "";

    private int expire = 0;

    private String principalIdFieldName = "authCacheKey or id";

    /**
     * Returns the Redis session keys
     * prefix.
     *
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     *
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getPrincipalIdFieldName() {
        return this.principalIdFieldName;
    }

    public void setPrincipalIdFieldName(String principalIdFieldName) {
        this.principalIdFieldName = principalIdFieldName;
    }

    /**
     * Constructs a cache instance with the specified
     * Redis manager and using a custom key prefix.
     *
     * @param redisManager The cache manager instance
     * @param prefix       The Redis key prefix
     */
    public RedisCache(IRedisManager redisManager, RedisSerializer keySerializer, RedisSerializer valueSerializer, String prefix, int expire, String principalIdFieldName) {
        if (redisManager == null) {
            throw new IllegalArgumentException("redisManager cannot be null.");
        } else {
            this.redisManager = redisManager;
            if (keySerializer == null) {
                throw new IllegalArgumentException("keySerializer cannot be null.");
            } else {
                this.keySerializer = keySerializer;
                if (valueSerializer == null) {
                    throw new IllegalArgumentException("valueSerializer cannot be null.");
                } else {
                    this.valueSerializer = valueSerializer;
                    if (prefix != null && !"".equals(prefix)) {
                        this.keyPrefix = prefix;
                    }

                    if (expire != -1) {
                        this.expire = expire;
                    }

                    if (principalIdFieldName != null && !"".equals(principalIdFieldName)) {
                        this.principalIdFieldName = principalIdFieldName;
                    }

                }
            }
        }
    }


    @Override
    public V get(K key) throws CacheException {
        logger.debug("get key [" + key + "]");
        if (key == null) {
            return null;
        } else {
            try {
                Object redisCacheKey = this.getRedisCacheKey(key);
                byte[] rawValue = this.redisManager.get(this.keySerializer.serialize(redisCacheKey));
                if (rawValue == null) {
                    return null;
                } else {
                    V value = (V)this.valueSerializer.deserialize(rawValue);
                    return value;
                }
            } catch (SerializationException var5) {
                throw new CacheException(var5);
            }
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        logger.debug("put key [" + key + "]");
        if (key == null) {
            logger.warn("Saving a null key is meaningless, return value directly without call Redis.");
            return value;
        } else {
            try {
                Object redisCacheKey = this.getRedisCacheKey(key);
                this.redisManager.set(this.keySerializer.serialize(redisCacheKey), value != null ? this.valueSerializer.serialize(value) : null, this.expire);
                return value;
            } catch (SerializationException var4) {
                throw new CacheException(var4);
            }
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        logger.debug("remove key [" + key + "]");
        if (key == null) {
            return null;
        } else {
            try {
                Object redisCacheKey = this.getRedisCacheKey(key);
                byte[] rawValue = this.redisManager.get(this.keySerializer.serialize(redisCacheKey));
                V previous = (V)this.valueSerializer.deserialize(rawValue);
                this.redisManager.del(this.keySerializer.serialize(redisCacheKey));
                return previous;
            } catch (SerializationException var5) {
                throw new CacheException(var5);
            }
        }
    }

    private Object getRedisCacheKey(K key) {
        return key == null ? null : (this.keySerializer instanceof StringSerializer ? this.keyPrefix + this.getStringRedisKey(key) : key);
    }

    private String getStringRedisKey(K key) {
        String redisKey;
        if (key instanceof PrincipalCollection) {
            redisKey = this.getRedisKeyFromPrincipalIdField((PrincipalCollection) key);
        } else {
            redisKey = key.toString();
        }

        return redisKey;
    }

    private String getRedisKeyFromPrincipalIdField(PrincipalCollection key) {
        Object principalObject = key.getPrimaryPrincipal();
        Method pincipalIdGetter = null;
        Method[] methods = principalObject.getClass().getDeclaredMethods();
        Method[] arr$ = methods;
        int len$ = methods.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            Method m = arr$[i$];
            if ("authCacheKey or id".equals(this.principalIdFieldName) && ("getAuthCacheKey".equals(m.getName()) || "getId".equals(m.getName()))) {
                pincipalIdGetter = m;
                break;
            }

            if (m.getName().equals("get" + this.principalIdFieldName.substring(0, 1).toUpperCase() + this.principalIdFieldName.substring(1))) {
                pincipalIdGetter = m;
                break;
            }
        }

        if (pincipalIdGetter == null) {
            throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName);
        } else {
            try {
                Object idObj = pincipalIdGetter.invoke(principalObject, new Object[0]);
                if (idObj == null) {
                    throw new PrincipalIdNullException(principalObject.getClass(), this.principalIdFieldName);
                } else {
                    String redisKey = idObj.toString();
                    return redisKey;
                }
            } catch (IllegalAccessException var10) {
                throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, var10);
            } catch (InvocationTargetException var11) {
                throw new PrincipalInstanceException(principalObject.getClass(), this.principalIdFieldName, var11);
            }
        }
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("clear cache");
        Set keys = null;

        try {
            keys = this.redisManager.keys(this.keySerializer.serialize(this.keyPrefix + "*"));
        } catch (SerializationException var4) {
            logger.error("get keys error", var4);
        }

        if (keys != null && keys.size() != 0) {
            Iterator i$ = keys.iterator();

            while (i$.hasNext()) {
                byte[] key = (byte[]) i$.next();
                this.redisManager.del(key);
            }

        }
    }

    @Override
    public int size() {
        Long longSize = Long.valueOf(0L);

        try {
            longSize = new Long(this.redisManager.dbSize(this.keySerializer.serialize(this.keyPrefix + "*")).longValue());
        } catch (SerializationException var3) {
            logger.error("get keys error", var3);
        }

        return longSize.intValue();
    }

    @Override
    public Set<K> keys() {
        Set keys = null;

        try {
            keys = this.redisManager.keys(this.keySerializer.serialize(this.keyPrefix + "*"));
        } catch (SerializationException var7) {
            logger.error("get keys error", var7);
            return Collections.emptySet();
        }

        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        } else {
            Set<K> convertedKeys = new HashSet();
            Iterator i$ = keys.iterator();

            while (i$.hasNext()) {
                byte[] key = (byte[]) i$.next();

                try {
                    convertedKeys.add((K)this.keySerializer.deserialize(key));
                } catch (SerializationException var6) {
                    logger.error("deserialize keys error", var6);
                }
            }

            return convertedKeys;
        }
    }

    @Override
    public Collection<V> values() {
        Set keys = null;

        try {
            keys = this.redisManager.keys(this.keySerializer.serialize(this.keyPrefix + "*"));
        } catch (SerializationException var8) {
            logger.error("get values error", var8);
            return Collections.emptySet();
        }

        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptySet();
        } else {
            List<V> values = new ArrayList(keys.size());
            Iterator i$ = keys.iterator();

            while (i$.hasNext()) {
                byte[] key = (byte[]) i$.next();
                Object value = null;

                try {
                    value = this.valueSerializer.deserialize(this.redisManager.get(key));
                } catch (SerializationException var7) {
                    logger.error("deserialize values= error", var7);
                }

                if (value != null) {
                    values.add((V)value);
                }
            }

            return Collections.unmodifiableList(values);
        }
    }
}
