package com.justdo.common.redis.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * 功能描述:自定义session生成器
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/26 下午3:21
 */
public class CustomSessionIdGenerator implements SessionIdGenerator {

	@Override
	 public Serializable generateId(Session session){

		return UUID.randomUUID().toString().replace("-","");
	}

}
