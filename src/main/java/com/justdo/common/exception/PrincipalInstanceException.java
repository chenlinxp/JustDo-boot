package com.justdo.common.exception;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午2:32
 */

public class PrincipalInstanceException extends RuntimeException {
	private static final String MESSAGE = "We need a field to identify this Cache Object in Redis. So you need to defined an id field which you can get unique id to identify this principal. For example, if you use UserInfo as Principal class, the id field maybe userId, userName, email, etc. For example, getUserId(), getUserName(), getEmail(), etc.\nDefault value is authCacheKey or id, that means your principal object has a method called \"getAuthCacheKey()\" or \"getId()\"";

	public PrincipalInstanceException(Class clazz, String idMethodName) {
		super(clazz + " must has getter for field: " + idMethodName + "\n" + "We need a field to identify this Cache Object in Redis. So you need to defined an id field which you can get unique id to identify this principal. For example, if you use UserInfo as Principal class, the id field maybe userId, userName, email, etc. For example, getUserId(), getUserName(), getEmail(), etc.\nDefault value is authCacheKey or id, that means your principal object has a method called \"getAuthCacheKey()\" or \"getId()\"");
	}

	public PrincipalInstanceException(Class clazz, String idMethodName, Exception e) {
		super(clazz + " must has getter for field: " + idMethodName + "\n" + "We need a field to identify this Cache Object in Redis. So you need to defined an id field which you can get unique id to identify this principal. For example, if you use UserInfo as Principal class, the id field maybe userId, userName, email, etc. For example, getUserId(), getUserName(), getEmail(), etc.\nDefault value is authCacheKey or id, that means your principal object has a method called \"getAuthCacheKey()\" or \"getId()\"", e);
	}
}
