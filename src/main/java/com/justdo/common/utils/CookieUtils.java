package com.justdo.common.utils;

import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.SimpleCookie;

/**
 * Cookie工具类
 *
 * @author: chenlin
 * @date:2019-06-12
 */

public class CookieUtils {

    public  static SimpleCookie get(){

	     return   (SimpleCookie)ApplicationContextUtils.getBean(CookieRememberMeManager.class).getCookie();

    }

	public  static SimpleCookie set(){

		SimpleCookie cookie = (SimpleCookie)ApplicationContextUtils.getBean(CookieRememberMeManager.class).getCookie();

		cookie.setMaxAge(-1);

		return cookie;

	}

}
