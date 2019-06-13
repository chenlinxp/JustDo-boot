package com.justdo.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * MD5加密工具类
 */
public class MD5Utils {
	private static final String SALT = "88888";

	private static final String ALGORITH_NAME = "md5";

	private static final int HASH_ITERATIONS = 2;

	public static String encrypt(String pswd) {
		String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static String encrypt(String username, String password) {
		String newPassword = new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(username + SALT),
				HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static String encrypt(String algorith_name, String password,String salt) {
		String newPassword = new SimpleHash(algorith_name, password, ByteSource.Util.bytes(salt),
				HASH_ITERATIONS).toHex();
		return newPassword;
	}

	public static void main(String[] args) {
		
		//System.out.println(MD5Utils.encrypt("admin", "1"));
	}

}
