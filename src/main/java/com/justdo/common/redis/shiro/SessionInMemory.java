package com.justdo.common.redis.shiro;

import org.apache.shiro.session.Session;

import java.util.Date;

/**
 * 功能描述
 * Use ThreadLocal as a temporary storage of Session, so that shiro wouldn't keep read redis several times while a request coming.
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/11/27 下午5:01
 */
public class SessionInMemory {
	private Session session;
	private Date createTime;

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
