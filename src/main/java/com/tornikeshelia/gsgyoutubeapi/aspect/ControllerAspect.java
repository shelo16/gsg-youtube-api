package com.tornikeshelia.gsgyoutubeapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    /**
     *
     * Triggers on any request of Controller package ( does nothing for security-s controller )
     * Logs basic info
     *
     * **/
    @Before("execution(* com.tornikeshelia.gsgyoutubeapi.controller.*.*(..))")
    public void preLogger(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        if (request != null){
            log.info("================Received Http Request================");
            log.info("HTTP METHOD = {}", request.getMethod());
            log.info("URI = {}", request.getRequestURI());
            log.info("QUERY = {}", request.getQueryString());
            log.info("CLASS_METHOD = {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            log.info("ARGS = {}", Arrays.toString(joinPoint.getArgs()));
            log.info("REQUESTER IP = {}", request.getRemoteAddr());
        }
    }

}
