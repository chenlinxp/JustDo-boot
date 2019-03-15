package com.justdo.common.exception;

import com.justdo.common.utils.HttpServletUtils;
import com.justdo.common.utils.IPUtils;
import com.justdo.common.utils.R;
import com.justdo.common.utils.ShiroUtils;
import com.justdo.system.errorlog.domain.ErrorLogDO;
import com.justdo.system.errorlog.service.ErrorLogService;
import com.justdo.system.user.domain.UserDO;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * 异常处理器
 */
@RestControllerAdvice
public class BDExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    ErrorLogService errorLogService;


    @ExceptionHandler(BDException.class)
    public R handleBDException(BDException e) {
        logger.error(e.getMessage(), e);
        R r = new R();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        return r;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public R noHandlerFoundException(org.springframework.web.servlet.NoHandlerFoundException e) {
        logger.error(e.getMessage(), e);
        return R.error(404, "没找找到页面");
    }

    @ExceptionHandler(AuthorizationException.class)
    public Object handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
        logger.error(e.getMessage(), e);
        if (HttpServletUtils.jsAjax(request)) {
            return R.error(403, "未授权");
        }
        return new ModelAndView("error/403");
    }


    @ExceptionHandler({Exception.class})
    public Object handleException(Exception e, HttpServletRequest request) {
        ErrorLogDO errorLogDO = new ErrorLogDO();
        errorLogDO.setCreateTime(new Date());
        UserDO current = ShiroUtils.getUser();
        if(null!=current) {
            errorLogDO.setUserId(current.getUserId());
            errorLogDO.setUserName(current.getUsername());
        }else {
            errorLogDO.setUserId("-1");
            errorLogDO.setUserName("获取用户信息为空");
        }
        errorLogDO.setExceptionContent(e.toString());
        errorLogDO.setExceptionState(0);
        errorLogDO.setRemark("");
        errorLogDO.setIp(IPUtils.getIpAddr(request));
        errorLogService.save(errorLogDO);
        logger.error(e.getMessage(), e);
        if (HttpServletUtils.jsAjax(request)) {
            return R.error(500, "服务器错误，请联系管理员");
        }
        return new ModelAndView("error/500");
    }
}
