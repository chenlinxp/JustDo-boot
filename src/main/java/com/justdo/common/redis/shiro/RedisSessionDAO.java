package com.justdo.common.redis.shiro;

import com.justdo.common.utils.SerializerUtils;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;

/**
 * @author justdo
 * @version V1.0
 */
public class RedisSessionDAO extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisSessionDAO.class);

    private static final String DEFAULT_SESSION_KEY_PREFIX = "shiro:session:";

    private String keyPrefix = DEFAULT_SESSION_KEY_PREFIX;

    private static final long DEFAULT_SESSION_IN_MEMORY_TIMEOUT = 1000L;
    /**
     * doReadSession be called about 10 times when login.
     * Save Session in ThreadLocal to resolve this problem. sessionInMemoryTimeout is expiration of Session in ThreadLocal.
     * The default value is 1000 milliseconds (1s).
     * Most of time, you don't need to change it.
     */
    private long sessionInMemoryTimeout = DEFAULT_SESSION_IN_MEMORY_TIMEOUT;

    /**
     * expire time in seconds
     */
    private static final int DEFAULT_EXPIRE = -2;
    private static final int NO_EXPIRE = -1;

    /**
     * Please make sure expire is longer than sesion.getTimeout()
     */
    private int expire = DEFAULT_EXPIRE;

    private static final int MILLISECONDS_IN_A_SECOND = 1000;

    private IRedisManager redisManager;

    private RedisSerializer keySerializer = new StringSerializer();

    private RedisSerializer valueSerializer = new ObjectSerializer();

    private static ThreadLocal sessionsInThread = new ThreadLocal();

    @Override
    public void update(Session session) throws UnknownSessionException {

        //如果会话过期/停止 没必要再更新了
        try {
            if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
                return;
            }
            if (session instanceof ShiroSession) {
                // 如果没有主要字段(除lastAccessTime以外其他字段)发生改变
                ShiroSession ss = (ShiroSession) session;
                if (!ss.isChanged()) {
                   // return;
                }
                //如果没有返回 证明有调用 setAttribute往redis 放的时候永远设置为false
                ss.setChanged(false);
            }
            logger.debug("update saveSession id is:" + session.getId());
            this.saveSession(session);
        }
        catch (Exception e){
            logger.warn("update Session is failed", e);
        }
    }

    /**
     * save session
     * @param session
     * @throws UnknownSessionException
     */
    private void saveSession(Session session) throws UnknownSessionException{


        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            throw new UnknownSessionException("session or session id is null");
        }
        byte[] key = getByteKey(session.getId());
        byte[] value = SerializerUtils.serialize(session);
        if (expire == DEFAULT_EXPIRE) {
            this.redisManager.set(key, value, (int) (session.getTimeout() / MILLISECONDS_IN_A_SECOND));
            return;
        }
        if (expire != NO_EXPIRE && expire * MILLISECONDS_IN_A_SECOND < session.getTimeout()) {
            logger.warn("Redis session expire time: "
                    + (expire * MILLISECONDS_IN_A_SECOND)
                    + " is less than Session timeout: "
                    + session.getTimeout()
                    + " . It may cause some problems.");
        }
        this.redisManager.set(key, value, expire);
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
        session.setTimeout(0);

    }

    //获取所有的session
    @Override
    public Collection<Session> getActiveSessions() {

        Set<Session> sessions = new HashSet<Session>();
        Set<byte[]> keys = redisManager.keys(this.keyPrefix + "*");
        if(keys != null && keys.size()>0){
            for(byte[] key:keys){
                Session s = (Session) SerializerUtils.deserialize(redisManager.get(key));
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
        Serializable sessionId;
        if(oldsessionId !=null) {

            redisManager.del(this.getByteKey(oldsessionId));

             sessionId = this.generateSessionId(session);

            this.assignSessionId(session, sessionId);
            this.saveSession(session);
        }else{
            //throw new UnknownSessionException("session or session id is null");
             sessionId = this.generateSessionId(session);
            this.assignSessionId(session, sessionId);
        }
        return sessionId;




    }

    //读取session
    @Override
    protected Session doReadSession(Serializable sessionId) {

        if (sessionId == null) {
            logger.warn("session id is null");
            return null;
        }

        Session s = getSessionFromThreadLocal(sessionId);
        logger.debug("doReadSession id is:"+sessionId);
        if (s != null) {
            return s;
        }

        logger.debug("read session from redis");
        try {
             s = (Session) SerializerUtils.deserialize(redisManager.get(this.getByteKey(sessionId)));

            setSessionToThreadLocal(sessionId, s);

        } catch (Exception e) {
            logger.error("read session error. settionId= {}",sessionId);
        }
        return s;
    }
    private void setSessionToThreadLocal(Serializable sessionId, Session s) {
        Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
        if (sessionMap == null) {
            sessionMap = new HashMap<Serializable, SessionInMemory>();
            sessionsInThread.set(sessionMap);
        }
        SessionInMemory sessionInMemory = new SessionInMemory();
        sessionInMemory.setCreateTime(new Date());
        sessionInMemory.setSession(s);
        sessionMap.put(sessionId, sessionInMemory);
    }

    private Session getSessionFromThreadLocal(Serializable sessionId) {
        Session s = null;

        if (sessionsInThread.get() == null) {
            return null;
        }

        Map<Serializable, SessionInMemory> sessionMap = (Map<Serializable, SessionInMemory>) sessionsInThread.get();
        SessionInMemory sessionInMemory = sessionMap.get(sessionId);
        if (sessionInMemory == null) {
            return null;
        }
        Date now = new Date();
        long duration = now.getTime() - sessionInMemory.getCreateTime().getTime();
        if (duration < sessionInMemoryTimeout) {
            s = sessionInMemory.getSession();
            logger.debug("read session from memory");
        } else {
            sessionMap.remove(sessionId);
        }

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

    public IRedisManager getRedisManager() {
        return this.redisManager;
    }

    public void setRedisManager(IRedisManager redisManager) {
        this.redisManager = redisManager;
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
     * @param session
     * @return
     */
    public String getLoginName(Session session){
        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
                .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        SimpleEmployeeDO simpleEmployeeDO = (SimpleEmployeeDO) principalCollection.getPrimaryPrincipal();
        return simpleEmployeeDO.getLoginName();
    }

    public long getSessionInMemoryTimeout() {
        return sessionInMemoryTimeout;
    }

    public void setSessionInMemoryTimeout(long sessionInMemoryTimeout) {
        this.sessionInMemoryTimeout = sessionInMemoryTimeout;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }
}