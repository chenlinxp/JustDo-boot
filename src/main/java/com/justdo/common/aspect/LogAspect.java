package com.justdo.common.aspect;

import com.justdo.common.annotation.Log;
import com.justdo.common.utils.DateUtils;
import com.justdo.common.utils.HttpContextUtils;
import com.justdo.common.utils.IPUtils;
import com.justdo.common.utils.ShiroUtils;
import com.justdo.system.employee.domain.SimpleEmployeeDO;
import com.justdo.system.operationlog.domain.OperationLogDO;
import com.justdo.system.operationlog.service.OperationLogService;
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
import java.util.Arrays;

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
    OperationLogService operationLogService;


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
        OperationLogDO operationLog = new OperationLogDO();
        Log log = method.getAnnotation(Log.class);
        if (log != null) {
            // 获取注解上的描述
            operationLog.setOperation(log.value());
        }
        //请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //请求的方法名
        String methodName = signature.getName();
        operationLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params =  Arrays.toString(args);
            operationLog.setParams(params);
        } catch (Exception e) {

        }
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        operationLog.setIp(IPUtils.getIpAddr(request));
        // 用户名
        SimpleEmployeeDO currUser = ShiroUtils.getSimpleEmployee();
        if (currUser == null) {
            operationLog.setUserId("-1");
            operationLog.setUserName("获取用户信息为空");
        } else {
            operationLog.setUserId(ShiroUtils.getEmployeeId());
            operationLog.setUserName(ShiroUtils.getSimpleEmployee().getLoginName());
        }
        logger.info("当前用户ID: " + operationLog.getUserId());
        logger.info("当前用户名: " + operationLog.getUserName());
        operationLog.setTime((int) time);
        // 系统当前时间
        operationLog.setCreateTime(DateUtils.formatTimeNow("yyyy-MM-dd HH:mm:ss"));
        // 保存系统日志
        operationLogService.save(operationLog);
    }
}
