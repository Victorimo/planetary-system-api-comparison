package com.planetary.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    
    /**
     * Pointcut for all methods in service layer
     */
    @Pointcut("execution(* com.planetary.service..*(..))")
    public void serviceLayer() {}
    
    /**
     * Pointcut for all methods in controller layer
     */
    @Pointcut("execution(* com.planetary.controller..*(..))")
    public void controllerLayer() {}
    
    /**
     * Pointcut for all methods in repository layer
     */
    @Pointcut("execution(* com.planetary.repository..*(..))")
    public void repositoryLayer() {}
    
    /**
     * Before advice for service layer methods
     * Logs method entry with parameters
     */
    @Before("serviceLayer()")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        
        log.info("==> [SERVICE] Entering method: {} with arguments: {}", 
                methodName, Arrays.toString(args));
    }
    
    /**
     * After returning advice for controller layer methods
     * Logs successful method execution with return value
     */
    @AfterReturning(pointcut = "controllerLayer()", returning = "result")
    public void logAfterControllerMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        
        log.info("<== [CONTROLLER] Method {} executed successfully. Return value: {}", 
                methodName, result != null ? result.getClass().getSimpleName() : "null");
    }
    
    /**
     * After throwing advice for controller layer methods
     * Logs exceptions thrown by controller methods
     */
    @AfterThrowing(pointcut = "controllerLayer()", throwing = "exception")
    public void logAfterControllerException(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().toShortString();
        
        log.error("<!> [CONTROLLER] Method {} threw exception: {}", 
                methodName, exception.getMessage());
    }
    
    /**
     * Around advice for repository layer methods
     * Logs method execution time and details
     */
    @Around("repositoryLayer()")
    public Object logAroundRepositoryMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        
        long startTime = System.currentTimeMillis();
        
        log.debug(">>> [REPOSITORY] Executing method: {} with arguments: {}", 
                methodName, Arrays.toString(args));
        
        Object result;
        try {
            result = joinPoint.proceed();
            
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.debug("<<< [REPOSITORY] Method {} completed successfully in {} ms. Result: {}", 
                    methodName, executionTime, 
                    result != null ? result.getClass().getSimpleName() : "null");
            
            return result;
            
        } catch (Throwable throwable) {
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.error("<!> [REPOSITORY] Method {} failed after {} ms with exception: {}", 
                    methodName, executionTime, throwable.getMessage());
            
            throw throwable;
        }
    }
    
    /**
     * After advice for service layer methods (both success and failure)
     * Logs method completion
     */
    @After("serviceLayer()")
    public void logAfterServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        
        log.info("--- [SERVICE] Method {} execution completed", methodName);
    }
}

