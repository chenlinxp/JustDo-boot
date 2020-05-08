package com.justdo.portal.controller;

import com.justdo.appmanage.app.domain.AppDO;
import com.justdo.appmanage.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 功能描述 短链接
 *
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2020/4/13 下午11:01
 */
@Controller
public class ShortController {

	@Autowired
	private AppService appService;

	@GetMapping({ "/{shortString}"})
	String redirectUrl(@PathVariable("shortString") String shortString) {

		if(shortString.equals("justdo")){
			return "redirect:/justdo/login";
		}
		if(shortString.equals("swagger-ui")){
			return "redirect:/swagger-ui.html";
		}
		AppDO app = appService.getByShortUrl(shortString);

		if(app!=null){
			return "forward:"+app.getLoadUrl();
		}
        else{
			return "error/404";
		}
	}
}
