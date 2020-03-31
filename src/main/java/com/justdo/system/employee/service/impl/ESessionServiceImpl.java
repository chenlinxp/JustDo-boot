package com.justdo.system.employee.service.impl;


import com.justdo.system.employee.domain.EmployeeOnline;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import com.justdo.system.employee.service.ESessionService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




/**
 * 员工session
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Service
public class ESessionServiceImpl implements ESessionService {

	private final SessionDAO sessionDAO;

	@Autowired
	public ESessionServiceImpl(SessionDAO sessionDAO) {
		this.sessionDAO = sessionDAO;
	}

	@Override
	public List<EmployeeOnline> list() {
		List<EmployeeOnline> list = new ArrayList<>();
		Collection<Session> sessions = sessionList();
		for (Session session : sessions) {
			EmployeeOnline employeeOnline = new EmployeeOnline();
			System.out.println(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			System.out.println(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
			if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
				continue;
			} else {
				SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) session
						.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				SimpleEmployeeDO simpleEmployeeDO = (SimpleEmployeeDO) principalCollection.getPrimaryPrincipal();
				employeeOnline.setLoginName(simpleEmployeeDO.getLoginName());
			}
			employeeOnline.setId((String) session.getId());
			employeeOnline.setHostIP(session.getHost());
			employeeOnline.setStartTime(session.getStartTimestamp());
			employeeOnline.setLastTime(session.getLastAccessTime());
			employeeOnline.setTimeout(session.getTimeout());
			list.add(employeeOnline);
		}
		return list;
	}

	@Override
	public List<SimpleEmployeeDO> listOnlineEmployee() {
		List<SimpleEmployeeDO> list = new ArrayList<>();
		SimpleEmployeeDO simpleEmployeeDO;
		Collection<Session> sessions = sessionList();
		for (Session session : sessions) {
			if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
				continue;
			} else {
				SimplePrincipalCollection  principalCollection = (SimplePrincipalCollection) session
						.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				simpleEmployeeDO = (SimpleEmployeeDO) principalCollection.getPrimaryPrincipal();
				list.add(simpleEmployeeDO);
			}
		}
		return list;
	}

	@Override
	public Collection<Session> sessionList() {
		return sessionDAO.getActiveSessions();
	}

	@Override
	public boolean  forceLogout(String sessionId) {
		Session session = sessionDAO.readSession(sessionId);
		sessionDAO.delete(session);
		session.setTimeout(0);
		return true;
	}

	@Override
	public boolean  forceLogout(String[] Ids) {

		for(String sessionId : Ids) {
			Session session = sessionDAO.readSession(sessionId);
			sessionDAO.delete(session);
			session.setTimeout(0);
		}
		return true;
	}
}
