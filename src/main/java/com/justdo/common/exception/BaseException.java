package com.justdo.common.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.justdo.common.utils.StringUtils;

/**
 * 构造参数有异常消息或者异常
 * 异常处理方式 exceptType
 * 异常处理页面 errorPage,页面上用${exception}获取异常信息;如果你的页面有可能从其他系统接收异常，那么还要从param.exception中获取
 * 异常处理页面是其他系统的吗? errorPageIsOtherApp 
 * TODO
 * @author magican
 * 2014-12-21上午1:41:06
 */
public class BaseException extends RuntimeException {
	private String errorPage;
	private boolean errorPageIsOtherApp = false;
	/**
	 * 异常类型,决定当非ajax方式访问服务器时,页面捕获到异常后,如何处理
	 */
	public static enum ExceptType {
		/**
		 * 页面捕获异常后返回上一页
		 */
		BACK,
		/**
		 * 页面捕获异常后不做任何处理
		 */
		NORMAL,
		/**
		 * 页面捕获异常后跳回登录页面
		 */
		LOGIN,
		/**
		 * 页面捕获异常后关闭对话框
		 */
		DGCLOSE,
		/**
		 * 页面捕获异常后关闭选项卡
		 */
		TABCLOSE,
		/**
		 * 页面捕获异常后刷新选项卡
		 */
		TABFRAME,
		/**
		 * 页面捕获异常后关闭当前页面(非选项卡,非对话框时调用window本身关闭方法)
		 */
		WINDOWCLOSE,
		/**
		 * 刷新页面(只在异步请求数据时使用,不要在提交表单的页面使用) 或者刷新表格(当调用表格go方法时)
		 */
		REFRESH
	}
	
	private static final long serialVersionUID = 1L;
	public static Map<String, String> EXCEPTINFOS = new HashMap<String, String>();
	static{
		Properties entityReferences = new Properties();
		InputStream is = BaseException.class.getResourceAsStream("error.properties");
		if (is == null)
			throw new IllegalStateException("Cannot find reference definition file [error.properties] as class path resource");
		try {
			try {
				entityReferences.load(is);
			} finally {
				is.close();
			}
		} catch (IOException ex) {
			throw new IllegalStateException("Failed to parse reference definition file [error.properties]: " + ex.getMessage());
		}
		Enumeration<?> e = entityReferences.propertyNames();
		String key,value;
		for (; e.hasMoreElements(); EXCEPTINFOS.put(key, value)) {
			key = (String) e.nextElement();
			value = StringUtils.trim(entityReferences.getProperty(key));
		}
	}
	
	
	
	protected Throwable rootCause = null;
	private ExceptType exceptType = ExceptType.NORMAL;
	
//	public String getMessage() {
//		if (this.rootCause instanceof SQLException) {
//			SQLException sthis = (SQLException) this.rootCause;
//			return ErrorContext.getErrorInfo(sthis.getErrorCode());
//		}
//		return super.getMessage();
//	}

	
	
	
	public ExceptType getExceptType() {
		return exceptType;
	}

	public boolean isErrorPageIsOtherApp() {
		return errorPageIsOtherApp;
	}

	public void setErrorPageIsOtherApp(boolean errorPageIsOtherApp) {
		this.errorPageIsOtherApp = errorPageIsOtherApp;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public void setExceptType(ExceptType exceptType) {
		this.exceptType = exceptType;
	}

	public BaseException(String strMessage) {
		super(strMessage);
	}

	public BaseException(Throwable e) {
		super(e);
		this.rootCause = e;
	}

	public BaseException(Throwable e, String message) {
		super(message);
		this.rootCause = e;
	}

	public BaseException(Throwable e, String message, String forwardPath) {
		super(message);
		this.rootCause = e;
	}
	
	public BaseException(String strMessage,ExceptType exceptType) {
		super(strMessage);
		this.exceptType = exceptType;
	}
	public BaseException(Throwable e,ExceptType exceptType) {
		super(e);
		this.rootCause = e;
		this.exceptType = exceptType;
	}

	public BaseException(Throwable e, String message,ExceptType exceptType) {
		super(message);
		this.rootCause = e;
		this.exceptType = exceptType;
	}
	public BaseException(Throwable e, String message, String forwardPath,ExceptType exceptType) {
		super(message);
		this.rootCause = e;
		this.exceptType = exceptType;
	}
	
	
	
	
	
	
	
	
	public BaseException(String strMessage,String errorPage) {
		super(strMessage);
		this.errorPage = errorPage;
	}


	public BaseException(Throwable e, String message, String forwardPath,String errorPage) {
		super(message);
		this.errorPage = errorPage;
		this.rootCause = e;
	}
	
	public BaseException(String strMessage,ExceptType exceptType,String errorPage) {
		super(strMessage);
		this.errorPage = errorPage;
		this.exceptType = exceptType;
	}
	public BaseException(Throwable e,ExceptType exceptType,String errorPage) {
		super(e);
		this.errorPage = errorPage;
		this.rootCause = e;
		this.exceptType = exceptType;
	}

	public BaseException(Throwable e, String message,ExceptType exceptType,String errorPage) {
		super(message);
		this.errorPage = errorPage;
		this.rootCause = e;
		this.exceptType = exceptType;
	}
	public BaseException(Throwable e, String message, String forwardPath,ExceptType exceptType,String errorPage) {
		super(message);
		this.errorPage = errorPage;
		this.rootCause = e;
		this.exceptType = exceptType;
	}
	
	
	
	
	public BaseException(String strMessage,String errorPage,boolean errorPageIsOtherApp) {
		super(strMessage);
		this.errorPageIsOtherApp = errorPageIsOtherApp;
		this.errorPage = errorPage;
	}


	public BaseException(Throwable e, String message, String forwardPath,String errorPage,boolean errorPageIsOtherApp) {
		super(message);
		this.errorPageIsOtherApp = errorPageIsOtherApp;
		this.errorPage = errorPage;
		this.rootCause = e;
	}
	
	public BaseException(String strMessage,ExceptType exceptType,String errorPage,boolean errorPageIsOtherApp) {
		super(strMessage);
		this.errorPageIsOtherApp = errorPageIsOtherApp;
		this.errorPage = errorPage;
		this.exceptType = exceptType;
	}
	public BaseException(Throwable e,ExceptType exceptType,String errorPage,boolean errorPageIsOtherApp) {
		super(e);
		this.errorPageIsOtherApp = errorPageIsOtherApp;
		this.errorPage = errorPage;
		this.rootCause = e;
		this.exceptType = exceptType;
	}

	public BaseException(Throwable e, String message,ExceptType exceptType,String errorPage,boolean errorPageIsOtherApp) {
		super(message);
		this.errorPageIsOtherApp = errorPageIsOtherApp;
		this.errorPage = errorPage;
		this.rootCause = e;
		this.exceptType = exceptType;
	}
	public BaseException(Throwable e, String message, String forwardPath,ExceptType exceptType,String errorPage,boolean errorPageIsOtherApp) {
		super(message);
		this.errorPageIsOtherApp = errorPageIsOtherApp;
		this.errorPage = errorPage;
		this.rootCause = e;
		this.exceptType = exceptType;
	}
	
	
	
	public BaseException() {
	}
//
//	public String getTypeDiscription() {
//		return "出现未知错误!";
//	}
//
//	public Throwable getRootCause() {
//		return this.rootCause;
//	}
//
//	public void setRootCause(Throwable rootCause) {
//		this.rootCause = rootCause;
//	}
}