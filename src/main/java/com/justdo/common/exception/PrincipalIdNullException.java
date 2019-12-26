package com.justdo.common.exception;

/**
 * 功能描述
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019/12/25 下午2:33
 */

public class PrincipalIdNullException extends RuntimeException {

	private static final String MESSAGE = "Principal Id shouldn't be null!";

	public PrincipalIdNullException(Class clazz, String idMethodName) {
		super(clazz + " id field: " + idMethodName + ", value is null\n" + "Principal Id shouldn't be null!");
	}
}