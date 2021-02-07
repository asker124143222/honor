package com.wood.honor.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @Author: xu.dm
 * @Date: 2021/2/4 15:11
 * @Version: 1.0
 * @Description: TODO
 **/
@Component
@Aspect
public class LogRequest {
    private Logger logger = LoggerFactory.getLogger(LogRequest.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) || " +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void log() {

    }

    @Before("log()")
    public void logBefore(JoinPoint joinPoint) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra != null;
        HttpServletRequest request =  sra.getRequest();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(LocalDateTime.now().toString()+" 方法执行前:");
        stringBuilder.append(",url:"+request.getRequestURI());
        stringBuilder.append(",ip:"+request.getRemoteAddr());
        stringBuilder.append(",method:"+request.getMethod());
        stringBuilder.append(",class_method:"+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        stringBuilder.append(",args:"+ Arrays.toString(joinPoint.getArgs()));

        logger.info(stringBuilder.toString());
    }
}
