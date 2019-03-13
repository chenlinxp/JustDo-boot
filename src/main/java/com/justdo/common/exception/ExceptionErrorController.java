package com.justdo.common.exception;

import com.justdo.common.utils.R;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常错误处理Controller
 * Created by chenlin on 2019/3/13.
 */
@Controller
public class ExceptionErrorController  extends BasicErrorController {

	public ExceptionErrorController(ServerProperties serverProperties) {
		super(new DefaultErrorAttributes(), serverProperties.getError());
	}

	/**
	 * 覆盖默认的Json响应
	 */
	@Override
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
		HttpStatus status = getStatus(request);
		int code = status.value();
		Map<String, Object> map = new HashMap<String, Object>();
		//输出自定义的Json格式
		if (404 == code) {
			map =  R.error(404, "未找到资源");
		} else if (403 == code) {
			map =  R.error(403, "没有访问权限");
		} else if (401 == code) {
			map =  R.error(401, "登录过期");
		} else {
			map =  R.error(500, "服务器错误");
		}

		return new ResponseEntity<Map<String, Object>>(map, status);
	}

	/**
	 * 覆盖默认的HTML响应
	 */
	@Override
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		//请求的状态
		HttpStatus status = getStatus(request);
		//response.setStatus(getStatus(request).value());
		int code = status.value();
        //Map<String, Object> model = getErrorAttributes(request,isIncludeStackTrace(request, MediaType.TEXT_HTML));
		//ModelAndView modelAndView = resolveErrorView(request, response, status, model);
		if (404 == code) {
			return new ModelAndView("error/404");
		} else if (403 == code) {
			return new ModelAndView("error/403");
		} else if (401 == code) {
			return new ModelAndView("login");
		} else {
			return new ModelAndView("error/500");
		}
	}
}


