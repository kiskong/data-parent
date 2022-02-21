package com.cingk.datameta.aspect;

import java.util.Enumeration;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: create by lvkc
 * @version: v1.0
 * @description:
 * @date:2022-02-21
 */
@Aspect
@Component
public class RequestAspect {

	Logger logger = LoggerFactory.getLogger(RequestAspect.class);

	@Pointcut("execution(* com.cingk.datameta.controller..*.*(..))")
	public void pointCut(){

	}

	@Before("pointCut()")
	public void saveRequestParam(JoinPoint joinPoint){
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
		String url = httpServletRequest.getRequestURI();
		String method = httpServletRequest.getMethod();

		String className = joinPoint.getSignature().getDeclaringTypeName();
		String classMethod = joinPoint.getSignature().getName();

		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

		//https://blog.csdn.net/qq_27037397/article/details/117218493
	}

}
