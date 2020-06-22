package edu.gdpu.myssm.aspect;

import edu.gdpu.myssm.annotation.Aspect;
import edu.gdpu.myssm.aop.Advice;
import edu.gdpu.myssm.aop.JoinPoint;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 00:37:15
 */
@Aspect("hello")
public class HelloAspect implements Advice {
    @Override
    public void before(JoinPoint joinPoint) {
        System.out.println("before....");
    }

    @Override
    public void after(JoinPoint joinPoint) {
        System.out.println("after....");
    }

    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {

    }

    @Override
    public Object afterReturning(JoinPoint joinPoint, Object o) {
        System.out.println("return....");
        System.out.println(joinPoint);
        return null;
    }
}
