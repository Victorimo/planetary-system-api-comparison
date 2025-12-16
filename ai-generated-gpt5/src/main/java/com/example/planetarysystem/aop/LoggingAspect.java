package com.example.planetarysystem.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    /**
     * Pointcut 1: Before all service methods.
     */
    @Pointcut("execution(* com.example.planetarysystem.service..*(..))")
    public void serviceMethods() {}

    /**
     * Pointcut 2: After all controller methods.
     */
    @Pointcut("execution(* com.example.planetarysystem.controller..*(..))")
    public void controllerMethods() {}

    /**
     * Pointcut 3: Around all repository methods.
     */
    @Pointcut("execution(* com.example.planetarysystem.repository..*(..))")
    public void repositoryMethods() {}

    @Before("serviceMethods()")
    public void logBeforeService(JoinPoint jp) {
        log.info("[SERVICE:BEFORE] {}.{} args={}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                Arrays.toString(jp.getArgs()));
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterController(JoinPoint jp, Object result) {
        log.info("[CONTROLLER:AFTER] {}.{} resultType={}",
                jp.getSignature().getDeclaringTypeName(),
                jp.getSignature().getName(),
                (result == null ? "void" : result.getClass().getSimpleName()));
    }

    @Around("repositoryMethods()")
    public Object logAroundRepository(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object res = pjp.proceed();
            long ms = System.currentTimeMillis() - start;
            log.debug("[REPOSITORY:AROUND] {}.{} took={}ms",
                    pjp.getSignature().getDeclaringTypeName(),
                    pjp.getSignature().getName(),
                    ms);
            return res;
        } catch (Throwable t) {
            long ms = System.currentTimeMillis() - start;
            log.warn("[REPOSITORY:AROUND] {}.{} failed after {}ms: {}",
                    pjp.getSignature().getDeclaringTypeName(),
                    pjp.getSignature().getName(),
                    ms,
                    t.getMessage());
            throw t;
        }
    }
}
