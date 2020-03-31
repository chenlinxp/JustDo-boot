package com.justdo.common.exception;

import com.justdo.common.utils.*;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import com.justdo.system.errorlog.domain.ErrorLogDO;
import com.justdo.system.errorlog.service.ErrorLogService;
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
 * <p>可对全局异常进行捕获，包括自定义异常
 * 需要清楚的是，其是应用于对springmvc中的控制器抛出的异常进行处理，
 * 而对于404这样不会进入控制器处理的异常不起作用，所以此时还是要依靠ErrorController来处理</p>
 * @author chenlin
 * @email chenlinxp@qq.com
 * @date 2019-03-30 15:36:21
 */
@RestControllerAdvice
public class CatchExceptionHandler {
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
        Date date = new Date();
        String nowtimeStr = DataFormater.toString(date,"yyyy-MM-dd HH:mm:ss");
        errorLogDO.setCreateTime(nowtimeStr);
        SimpleEmployeeDO current = ShiroUtils.getSimpleEmployee();
        if(null!=current) {
            errorLogDO.setUserId(current.getId());
            errorLogDO.setUserName(current.getLoginName());
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
