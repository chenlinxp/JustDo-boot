package com.justdo.common.aspect;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.HttpContextUtils;
import com.justdo.common.utils.IPUtils;
import com.justdo.common.utils.JSONUtils;
import com.justdo.common.utils.ShiroUtils;
import com.justdo.system.log.domain.LogDO;
import com.justdo.system.log.service.LogService;
import com.justdo.system.user.domain.UserDO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 记录自定义@Log注解的日志
 *
 * @author chenlin
 * @email 13233669915@qq.com
 * @date 2018-06-19 16:02:20
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    LogService logService;


    @Pointcut("@annotation(com.justdo.common.annotation.Log)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();

        long endTime = System.currentTimeMillis();
        //异步保存日志
        saveLog(point, beginTime - endTime);
        return result;
    }

    /**
     * 异步保存日志
     * @param joinPoint
     * @param time 执行时长(毫秒)
     * @throws InterruptedException
     */
    void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogDO sysLog = new LogDO();
        Log log = method.getAnnotation(Log.class);
        if (log != null) {
            // 获取注解上的描述
            sysLog.setOperation(log.value());
        }
        //请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //请求的方法名
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSONUtils.beanToJson(args[0]).substring(0, 4999);
            sysLog.setParams(params);
        } catch (Exception e) {

        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(IPUtils.getIpAddr(request));
        // 用户名
        UserDO currUser = ShiroUtils.getUser();
        if (currUser == null) {
            sysLog.setUserId("-1");
            sysLog.setUsername("获取用户信息为空");
        } else {
            sysLog.setUserId(ShiroUtils.getUserId());
            sysLog.setUsername(ShiroUtils.getUser().getUsername());
        }
        logger.info("当前用户ID: " + sysLog.getUserId());
        logger.info("当前用户名: " + sysLog.getUsername());
        sysLog.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        sysLog.setGmtCreate(date);
        // 保存系统日志
        logService.save(sysLog);
    }
}
