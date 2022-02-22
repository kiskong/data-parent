package com.cingk.datameta.aspect;

import java.util.Enumeration;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.cingk.datameta.model.dto.ResponseDto;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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

	/**
	 * 定义切入点 com.cingk.datameta.controller包下所有类和方法定义为切入点
	 */
	@Pointcut("execution(* com.cingk.datameta.controller..*.*(..))")
	public void pointCut() {

	}

	/**
	 * 在切入点之前执行
	 */
	@Before("pointCut()")
	public void saveRequestParam(JoinPoint joinPoint) {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
		//请求URI
		String requestURI = httpServletRequest.getRequestURI();

		//请求的类型
		String requestMethod = httpServletRequest.getMethod();

		//请求的类
		String className = joinPoint.getSignature().getDeclaringTypeName();
		//请求的方法
		String classMethod = joinPoint.getSignature().getName();
		logger.debug("============= HttpRequest start =============");
		logger.debug("URI: {} ", requestURI);
		logger.debug("HTTP Method: {} ", requestMethod);
		logger.debug("Class Method: {}.{}  ", className, classMethod);

		//请求头的信息
		Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			logger.debug("Head Name: {} = {}", headerName, httpServletRequest.getHeader(headerName));
		}
		//请求参数
		Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String param = parameterNames.nextElement();
			logger.debug("Parameter : {} = {}", param, httpServletRequest.getParameter(param));
		}

		//https://blog.csdn.net/qq_27037397/article/details/117218493
	}

	/**
	 * 在切入点正常返回后执行，逻辑程序异常时不执行
	 */
	@AfterReturning(returning = "object", pointcut = "pointCut()")
	public void saveResponseValue(Object object) {
		ResponseDto responseDto = (ResponseDto) object;
		logger.debug("ResponseDto: {}", JSON.toJSONString(responseDto));
		logger.debug("============= HttpRequest end =============");
	}

	/**
	 * 在切入点结束后执行，即使逻辑程序异常时亦会执行
	 */
	@After("pointCut()")
	public void afterLog(JoinPoint joinPoint) {

	}

	/**
	 * 逻辑程序异常时执行
	 */
	@AfterThrowing(throwing = "exception",pointcut = "pointCut()")
	public void exceptionLog(Exception exception) {

	}

}
