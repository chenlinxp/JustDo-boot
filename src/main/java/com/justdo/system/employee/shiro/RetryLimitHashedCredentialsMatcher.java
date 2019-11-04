package com.justdo.system.employee.shiro;


import com.justdo.common.redis.RedisManager;
import com.justdo.common.utils.SerializeUtils;
import com.justdo.system.employee.dao.EmployeeDao;
import com.justdo.system.employee.domain.EmployeeDO;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 登录次数大于5次，锁定账号
 * @author: chenlin
 * @date:2019-06-12
 * @description: 登陆次数限制
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private static final Logger logger = Logger.getLogger(RetryLimitHashedCredentialsMatcher.class);

	public static final String DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX = "shiro:cache:retrylimit:";

	private String keyPrefix = DEFAULT_RETRYLIMIT_CACHE_KEY_PREFIX;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private RedisManager redisManager;

	public void setRedisManager(RedisManager redisManager) {
		this.redisManager = redisManager;
	}
	/**
	 * 获得byte[]型的key
	 * @param key
	 * @return
	 */
	private byte[] getByteKey(String key){
		if(key instanceof String){
			String preKey = this.keyPrefix + key;
			return preKey.getBytes();
		}else{
			return SerializeUtils.serialize(key);
		}
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

		//获取用户名
		String username = (String) token.getPrincipal();
	    byte[] a = redisManager.get(getByteKey(username));
	    Integer retryCount = 0;
		//获取用户登录次数
		if (a != null) {
            String b = new String(a);
			//如果用户没有登陆过,登陆次数加1 并放入缓存String s = new String(bytes);
			retryCount = Integer.parseInt(new String(a));
		}
		if (retryCount > 5) {
			//如果用户登陆失败次数大于5次 抛出锁定用户异常  并修改数据库字段
			EmployeeDO employeeDO = employeeDao.findByEmployeeName(username);
			if (employeeDO != null && "1".equals(employeeDO.getEmployeeState().toString())) {
				//数据库字段 默认为 1  就是正常状态 所以 要改为0
				//修改数据库的状态字段为锁定
				employeeDO.setEmployeeState(0);
				employeeDao.update(employeeDO);
			}
			logger.info("锁定用户" + employeeDO.getLoginName());
			//抛出用户锁定异常
			throw new LockedAccountException();
		}
		//判断用户账号和密码是否正确
		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			//如果正确,从缓存中将用户登录计数 清除
			if (a != null) {
			redisManager.del(getByteKey(username));
			}
		}
		else{
			retryCount++;
			redisManager.set(getByteKey(username),retryCount.toString().getBytes());
		}
		return matches;
	}

	/**
	 * 根据用户名 解锁用户
	 *
	 * @param employeeDO
	 * @return
	 */
	public boolean unlockAccount(EmployeeDO employeeDO) {
		Boolean flag = false;
			//修改数据库的状态字段为解锁状态
		employeeDO.setEmployeeState(1);
	    String username = employeeDO.getLoginName();

			if(employeeDao.update(employeeDO)>0){
				byte[] a = redisManager.get(getByteKey(username));
				if (a != null) {
					redisManager.del(getByteKey(username));
				}
				flag = true;
			}

		return flag;
	}

}