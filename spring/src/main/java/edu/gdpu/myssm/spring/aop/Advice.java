package edu.gdpu.myssm.spring.aop;

/**
 * @author 嘿 林梓鸿
 */
public interface Advice {

    void before(JoinPoint joinPoint);

    void after(JoinPoint joinPoint);

    void afterThrowing(JoinPoint joinPoint, Throwable e);

    Object afterReturning(JoinPoint joinPoint, Object o);

}
