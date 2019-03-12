package com.justdo.system.user.service;


import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.justdo.system.user.domain.UserDO;
import com.justdo.system.user.domain.UserOnline;

/**
 * 用户
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 * @Description
 */
@Service
public interface SessionService {
	/**
	 * 在线用户的详细信息
	 * @return
	 */
	List<UserOnline> list();

	/**
	 * 在线用户信息（实体）
	 * @return
	 */
	List<UserDO> listOnlineUser();

	/**
	 *在线用户session的集合
	 * @return
	 */
	Collection<Session> sessionList();

	/**
	 *注销用户session
	 * @param sessionId
	 * @return
	 */
	boolean forceLogout(String sessionId);
}
