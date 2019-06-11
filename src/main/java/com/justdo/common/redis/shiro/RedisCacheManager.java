package com.justdo.common.redis.shiro;

/**
 * @author justdo
 * @version V1.0
 */

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

//public class RedisCacheManager implements CacheManager {
//
//    @Override
//    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
//        logger.debug("获取名称为: " + name + " 的RedisCache实例");
//
//        Cache c = caches.get(name);
//
//        if (c == null) {
//
//            // initialize the Redis manager instance
//            redisManager.init();
//
//            // create a new cache instance
//            c = new RedisCache<K, V>(redisManager, keyPrefix);
//
//            // add it to the cache collection
//            caches.put(name, c);
//        }
//        return c;
//    }

//}

    public class RedisCacheManager extends AbstractCacheManager {

        private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

        private RedisTemplate<byte[],byte[]> redisTemplate;

//        private String prefix = "shiro_redis:";
//
//        public String getPrefix() {
//            return prefix;
//        }
//
//        public void setPrefix(String prefix) {
//            this.prefix = prefix;
//        }


        public RedisCacheManager(RedisTemplate redisTemplate){
            this.redisTemplate = redisTemplate;
        }

//        public RedisCacheManager(RedisTemplate redisTemplate,String prefix){
//            this(redisTemplate);
//            this.prefix = prefix;
//        }

        /**
         * 为了个性化配置redis存储时的key，我们选择了加前缀的方式，
         * 所以写了一个带名字及redis操作的构造函数的Cache类
         * @param prefix
         * @return
         * @throws CacheException
         */
        @Override
        public Cache createCache(String prefix) throws CacheException {
            return new RedisCache(redisTemplate,prefix);
        }
    }

