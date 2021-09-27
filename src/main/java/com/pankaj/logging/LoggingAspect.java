package com.pankaj.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("execution (* com.pankaj.*.*(..))")
	public void logBeforeHome(JoinPoint joinPoint) {
		logger.info("Execution of Before Advice");
	}

	@AfterReturning("execution (* com.pankaj.*.*(..))")
	public void logAfterReturningGetUser(JoinPoint joinPoint) {
		logger.info("Execution of After Returning Advice");
	}

	@AfterThrowing("execution (* com.pankaj.*.*(..))")
	public void logAfterThrowinguser(JoinPoint joinPoint) {
		logger.info("Execution of After throwing Advice");
	}

	@Pointcut("execution (* com.pankaj.*.*(..))")
	public void demopointcut() {
	}

	@Around("execution (* com.pankaj.*.*(..))")
	public Object demoaroundcut(ProceedingJoinPoint proc) {
		Object val = null;
		try {
			val = proc.proceed();
		} catch (Throwable e) {

		}
		return val;

	}
}
