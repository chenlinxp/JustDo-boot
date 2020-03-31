package com.justdo.common.utils;

import com.justdo.common.redis.shiro.RedisSessionDAO;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Collection;
import java.util.List;


/**
 * Shiro工具类
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
public class ShiroUtils {

    private static final Logger logger = LoggerFactory.getLogger(ShiroUtils.class);
    @Autowired
    private static RedisSessionDAO sessionDAO;



    public static Subject getSubjct() {

        return SecurityUtils.getSubject();
    }

    /**
     * 员工对象
     * @return
     */
    public static SimpleEmployeeDO getSimpleEmployee() {

        Object object = getSubjct().getPrincipal();
        return (SimpleEmployeeDO)object;
    }

    /**
     * 员工ID
     * @return
     */
    public static String getEmployeeId() {

        return getSimpleEmployee().getId();
    }


    /**
     * 退出登录
     */
    public static void logout() {


        Session session = getSubjct().getSession();

        getSubjct().logout();

        CookieUtils.set();

        try {
            sessionDAO.delete(session);
        }
        catch(Exception e){
            logger.info("清除session异常信息："+ e.getMessage());
            logger.info("清除session异常的session："+ session.getId());
        }
    }

    public static List<Principal> getPrinciples() {

        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                principals.add((Principal)principalCollection.getPrimaryPrincipal());
            }
        }
        return principals;
    }

 
}
