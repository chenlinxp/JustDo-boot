package com.justdo.common.redis.shiro;

import com.justdo.common.redis.RedisManager;
import com.justdo.common.utils.SerializeUtils;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author justdo
 * @version V1.0
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);
    /**
     * shiro-redis的session对象前缀
     */
    private RedisManager redisManager;

    /**
     * The Redis key prefix for the sessions
     */
    private String keyPrefix = "shiro_redis_session:";

//    @Autowired
//    private RedisCacheManager cacheManager;
//
//    private String keyPrefix2 = "shiro_redis_cache:";
//
//    private Cache<String, Deque<Serializable>> cache;

    @Override
    public void update(Session session) throws UnknownSessionException {
        logger.debug("update saveSession id is:"+session.getId());
        this.saveSession(session);
    }

    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException{
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        //String loginName = getLoginName(session);
        byte[] key = getByteKey(session.getId());
        byte[] value = SerializeUtils.serialize(session);
        session.setTimeout(redisManager.getExpire()*60*30);
        this.redisManager.set(key, value, redisManager.getExpire());
    }

    // 删除session
    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null){
            logger.error("session or session id is null");
            return;
        }
        //String loginName = getLoginName(session);
        logger.debug("deleteSession id is:"+session.getId());
        redisManager.del(this.getByteKey(session.getId()));



    }

    //获取所有的session
    @Override
    public Collection<Session> getActiveSessions() {

        Set<Session> sessions = new HashSet<Session>();
        Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
        if(keys != null && keys.size()>0){
            for(byte[] key:keys){
                Session s = (Session)SerializeUtils.deserialize(redisManager.get(key));
                sessions.add(s);
            }
        }
        return sessions;
    }


    //设置session的最后一次访问时间
    @Override
    protected Serializable doCreate(Session session) {

        Serializable oldsessionId = session.getId();

        logger.info("doCreate deleteSession id is:"+oldsessionId);
        redisManager.del(this.getByteKey(oldsessionId));

        Serializable sessionId = this.generateSessionId(session);

        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    //读取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        if(sessionId == null){
            logger.error("doReadSession id is null");
            return null;
        }
        logger.debug("doReadSession id is:"+sessionId);
        logger.debug("doReadSession value is:"+redisManager.get(this.getByteKey(sessionId)));
        Session s = (Session)SerializeUtils.deserialize(redisManager.get(this.getByteKey(sessionId)));

        return s;
    }

    /**
     * 获得byte[]型的key
     * @param sessionId
     * @return
     */
    private byte[] getByteKey(Serializable sessionId){
        String preKey = this.keyPrefix + sessionId;

        return preKey.getBytes();
    }

    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;

        /**
         * 初始化redisManager
         */
        this.redisManager.init();
    }

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

    /**
     *
     * @param session
     * @return
     */
    public String getLoginName(Session session){
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
                .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        SimpleEmployeeDO simpleEmployeeDO = (SimpleEmployeeDO) principalCollection.getPrimaryPrincipal();
        return simpleEmployeeDO.getLoginName();
    }
}