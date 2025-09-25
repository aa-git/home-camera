package com.example.demo.Logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    @Around("@annotation(logExecution)")
    public Object logAround(ProceedingJoinPoint joinPoint, LogExecution logExecution) throws Throwable{
        System.out.println("before method: "+logExecution.parameter());
        Object result = joinPoint.proceed();
        System.out.println("after method: "+logExecution.parameter());
        return result;
    }
	
}
