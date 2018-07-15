package com.justdo.common.controller;

import org.springframework.stereotype.Controller;
import com.justdo.system.user.domain.UserToken;
import com.justdo.common.utils.ShiroUtils;
import com.justdo.system.user.domain.UserDO;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public String getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}