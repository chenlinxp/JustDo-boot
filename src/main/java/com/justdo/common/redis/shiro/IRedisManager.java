package com.justdo.common.redis.shiro;

import java.util.Set;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午2:39
 */
public interface IRedisManager {


	byte[] get(byte[] var1);

	/**
	 * set
	 * @param var1
	 * @param var2
	 * @param var3
	 * @return
	 */
	byte[] set(byte[] var1, byte[] var2, int var3);

	void del(byte[] var1);

	Long dbSize(byte[] var1);

	/**
	 * keys
	 * @param patter
	 * @return
	 */
	Set<byte[]> keys(byte[] patter);

	/**
	 * keys
	 * @param patter
	 * @return
	 */
	Set<byte[]> keys(String patter);


}
