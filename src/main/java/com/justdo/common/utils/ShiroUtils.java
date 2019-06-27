package com.justdo.common.utils;

import com.justdo.common.redis.shiro.RedisSessionDAO;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
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

        return getSimpleEmployee().getEmployeeId();
    }

    /**
     * 用户对象
     * @return
     */
//    public static UserDO getUser() {
//
//        Object object = getSubjct().getPrincipal();
//        return (UserDO)object;
//    }

    /**
     * 用户ID
     * @return
     */
//    public static String getUserId() {
//
//        return getUser().getUserId();
//    }


    public static void logout() {

        getSubjct().logout();

        Session session = getSubjct().getSession();

        sessionDAO.delete(session);


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
