package com.aa.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	// setup logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// setup pointcut declaration 
	@Pointcut("execution(* com.aa.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.aa.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage()")
	private void forAppFlow() {}
	
	// add @Before Advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		
		// display the method
		String theMethod = joinPoint.getSignature().toShortString();
		logger.info("=====> in @Before calling method: " + theMethod);
		
		// get the arguments
		Object[] args = joinPoint.getArgs();
		
		// display args
		for (Object tempArgs : args) {
			logger.info("=====> argument:" + tempArgs);
		}
	}
	
	// add @AfterReturning declaration
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult"
			)
	public void afterReturning(JoinPoint joinPoint, Object theResult) {
		
		// display the method
		String theMethod = joinPoint.getSignature().toShortString();
		logger.info("=====> in @AfterReturning calling method: " + theMethod);
		
		// display data returned
		logger.info("=====> result: " + theResult);
	}
	
}



















