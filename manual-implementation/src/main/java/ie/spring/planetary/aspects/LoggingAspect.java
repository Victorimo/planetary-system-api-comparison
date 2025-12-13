package ie.spring.planetary.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    // Pointcut 1: All controller methods
    @Pointcut("execution(* ie.spring.planetary.controllers.*.*(..))")
    public void controllerMethods() {}

    // Pointcut 2: All service methods with @Transactional
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void transactionalMethods() {}

    // Pointcut 3: All repository methods
    @Pointcut("execution(* ie.spring.planetary.repositories.*.*(..))")
    public void repositoryMethods() {}

    // ========== Pointcut 1: Controller Methods ==========
    @Before("controllerMethods()")
    public void logBeforeController(JoinPoint joinPoint) {
        log.info("[CONTROLLER BEFORE] Method: {}.{} | Parameters: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @After("controllerMethods()")
    public void logAfterController(JoinPoint joinPoint) {
        log.info("[CONTROLLER AFTER] Method: {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "controllerMethods()", returning = "result")
    public void logAfterReturningController(JoinPoint joinPoint, Object result) {
        log.info("[CONTROLLER RETURNING] Method: {}.{} | Return: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result != null ? result.getClass().getSimpleName() : "null");
    }

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public void logAfterThrowingController(JoinPoint joinPoint, Exception ex) {
        log.error("[CONTROLLER THROWING] Method: {}.{} | Exception: {} - {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }

    // ========== Pointcut 2: Service Methods with @Transactional ==========
    @Before("transactionalMethods()")
    public void logBeforeTransactional(JoinPoint joinPoint) {
        log.info("[TRANSACTIONAL BEFORE] Method: {}.{} | Parameters: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @After("transactionalMethods()")
    public void logAfterTransactional(JoinPoint joinPoint) {
        log.info("[TRANSACTIONAL AFTER] Method: {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "transactionalMethods()", returning = "result")
    public void logAfterReturningTransactional(JoinPoint joinPoint, Object result) {
        log.info("[TRANSACTIONAL RETURNING] Method: {}.{} | Return: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result != null ? result.getClass().getSimpleName() : "null");
    }

    @AfterThrowing(pointcut = "transactionalMethods()", throwing = "ex")
    public void logAfterThrowingTransactional(JoinPoint joinPoint, Exception ex) {
        log.error("[TRANSACTIONAL THROWING] Method: {}.{} | Exception: {} - {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }

    // ========== Pointcut 3: Repository Methods ==========
    @Before("repositoryMethods()")
    public void logBeforeRepository(JoinPoint joinPoint) {
        log.info("[REPOSITORY BEFORE] Method: {}.{} | Parameters: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @After("repositoryMethods()")
    public void logAfterRepository(JoinPoint joinPoint) {
        log.info("[REPOSITORY AFTER] Method: {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "repositoryMethods()", returning = "result")
    public void logAfterReturningRepository(JoinPoint joinPoint, Object result) {
        if (result != null) {
            if (result instanceof java.util.List) {
                log.info("[REPOSITORY RETURNING] Method: {}.{} | Return: List with {} items",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        ((java.util.List<?>) result).size());
            } else {
                log.info("[REPOSITORY RETURNING] Method: {}.{} | Return: {}",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        result.getClass().getSimpleName());
            }
        } else {
            log.info("[REPOSITORY RETURNING] Method: {}.{} | Return: null",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
        }
    }

    @AfterThrowing(pointcut = "repositoryMethods()", throwing = "ex")
    public void logAfterThrowingRepository(JoinPoint joinPoint, Exception ex) {
        log.error("[REPOSITORY THROWING] Method: {}.{} | Exception: {} - {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }
}

