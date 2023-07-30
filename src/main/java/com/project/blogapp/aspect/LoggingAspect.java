package com.project.blogapp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around(value = "com.project.blogapp.aspect.AppPointcuts.servicePointcut()")
    public Object logServiceMethodExecutionTimeAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        logger.info("Execution time of " + methodSignature.getDeclaringType().getSimpleName() + "."
                + methodSignature.getName() + " : " + stopWatch.getTotalTimeMillis() + "ms");
        return result;
    }

    @Before(value = "com.project.blogapp.aspect.AppPointcuts.searchPostsPointcut()")
    public void beforeSearchPostsAdvice(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        logger.info("Searched " + args[0]);
    }
}
