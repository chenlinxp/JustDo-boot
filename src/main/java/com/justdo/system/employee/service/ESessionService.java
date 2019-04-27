package com.justdo.system.employee.service;

import com.justdo.system.employee.domain.EmployeeOnline;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 员工session
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 * @Description
 */
@Service
public interface ESessionService {
	/**
	 * 在线员工的详细信息
	 * @return
	 */
	List<EmployeeOnline> list();

	/**
	 * 在线员工信息（实体）
	 * @return
	 */
	List<SimpleEmployeeDO> listOnlineEmployee();

	/**
	 *在线员工session的集合
	 * @return
	 */
	Collection<Session> sessionList();

	/**
	 *注销员工session
	 * @param sessionId
	 * @return
	 */
	boolean forceLogout(String sessionId);
}
