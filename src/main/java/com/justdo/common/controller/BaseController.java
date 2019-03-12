package com.justdo.common.controller;

import com.justdo.common.utils.ShiroUtils;
import com.justdo.system.user.domain.UserDO;
import org.springframework.stereotype.Controller;

/**
 *
 * 基础Controller
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 18:32:20
 */
@Controller
public class BaseController {

	/**
	 * 获取当前对象
	 * @return UserDO
	 */
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	/**
	 * 得到当前用户ID
	 * @return
	 */
	public String getUserId() {
		return getUser().getUserId();
	}

	/**
	 * 得到当前用户名
	 * @return
	 */
	public String getUsername() {
		return getUser().getUsername();
	}
}