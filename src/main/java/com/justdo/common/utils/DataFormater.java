package com.justdo.common.utils;

import java.math.BigDecimal;
import java.sql.Clob;
import java.text.ParseException;
import java.util.Date;


/**
 * 数据类型转换工具
 * 
 * @author Jing.Zhuo
 */
public class DataFormater {
	
	/**
	 * 基本数据类型转换
	 * 
	 * @param clz 类型
	 * @param val 值
	 * @return 字段类型的值
	 * @throws ParseException 日期转换异常
	 */
	public static <T>T convertType(Class<T> clz, String val) throws ParseException {
		return (T)convertType(clz, val, null);
	}
	
	/**
	 * 基本数据类型转换
	 * 
	 * @param clz 类型
	 * @param val 值
	 * @param pattern 时间格式
	 * @return 字段类型的值
	 * @throws ParseException 日期转换异常
	 */
	public static Object convertType(Class clz, String val, String pattern) throws ParseException {
		if (val == null || "".equals(val.trim())) {
			if (clz == byte.class)
				return 0;
			if (clz == short.class)
				return 0;
			if (clz == int.class)
				return 0;
			if (clz == long.class)
				return 0L;
			if (clz == float.class)
				return 0.0f;
			if (clz == double.class)
				return 0.0d;
			if (clz == char.class)
				return 0;
			if (clz == boolean.class)
				return false;
			return null;
		}

		if (clz == Byte.class || clz == byte.class)
			return Byte.parseByte(val);
		if (clz == Integer.class || clz == int.class)
			return Integer.parseInt(val);
		if (clz == Short.class || clz == short.class)
			return Short.parseShort(val);
		if (clz == Long.class || clz == long.class)
			return Long.parseLong(val);
		if (clz == Float.class || clz == float.class)
			return Float.parseFloat(val);
		if (clz == Double.class || clz == double.class)
			return Double.parseDouble(val);
		if (clz == Character.class || clz == char.class)
			return val.length() > 0 ? val.charAt(0) : 0;
		if (clz == Boolean.class || clz == boolean.class)
			return Boolean.parseBoolean(val);
		if (clz.isEnum() || Enum.class.isAssignableFrom(clz))
			return Enum.valueOf(clz, val);
		if (clz == BigDecimal.class)
			return new BigDecimal(val);

		if (clz == Date.class) {
			return DateUtils.convertStrToDate(val, pattern);
		}

		return val;
	}
	
	/**
	 * 将对象转换为对应格式的字符串
	 * 
	 * @param obj 需要转换的对象
	 * @return
	 */
	public static String toString(Object obj) {
		return toString(obj,null);
	}
	
	

	/**
	 * 将对象转换为对应格式的字符串
	 * 
	 * @param obj 需要转换的对象
	 * @param pattern 时间格式
	 * @return
	 */
	public static String toString(Object obj,String pattern) {
		if (obj == null)
			return null;
		if (obj instanceof Date) {
			return DateUtils.convertDateToStr((Date)obj, pattern);
		}
		if(obj instanceof Clob){
			return OracleSql.oracleClob2Str((Clob)obj);
			
		}
		return obj.toString();
	}
}
