package edu.gdpu.myssm.spring;

import edu.gdpu.myssm.spring.annotation.Transaction;
import edu.gdpu.myssm.spring.annotation.UseAspect;
import edu.gdpu.myssm.spring.aop.Advice;
import edu.gdpu.myssm.spring.aop.JoinPoint;
import edu.gdpu.myssm.spring.aop.Signature;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 14:28:11
 */
public class ProxyBeanFactory {

    private static Map<String,Object> aspects = new ConcurrentHashMap<>();

    public static Map<String, Object> getAspects() {
        return aspects;
    }

    private ProxyBeanFactory(){
    }

    public static Object getBean(final Object target){
        Class cl = target.getClass();
        UseAspect annotation = (UseAspect) cl.getAnnotation(UseAspect.class);
        final Advice advice;
        if(annotation.value().equals("")){
            advice = (Advice) aspects.get(cl.getSimpleName().toLowerCase()+"aspect");
        }else {
            advice = (Advice) aspects.get(annotation.value());
        }
        final String[] exclude = annotation.exclude();
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                String name = method.getName();
                if(name.equals("toString")||name.equals("equals")||name.equals("hashCode")){
                    return method.invoke(target, objects);
                }
                for (String s : exclude) {
                    if (name.equals(s)) {
                        return method.invoke(target, objects);
                    }
                }
                Signature signature = new Signature(name, method.getGenericReturnType(), method.getParameterTypes());
                JoinPoint joinPoint = new JoinPoint(target, signature, objects, this);
                advice.before(joinPoint);
                Object invoke = null;
                try {
                    invoke = method.invoke(target, objects);
                    advice.after(joinPoint);
                } catch (Exception e) {
                    advice.afterThrowing(joinPoint,e);
                }
                return advice.afterReturning(joinPoint, invoke);
            }
        };
        return Proxy.newProxyInstance(cl.getClassLoader(), cl.getInterfaces(),invocationHandler);
    }

    public static Object getTransactionBean(final Object target){
        Class cl = target.getClass();
        final Advice advice;
        Transaction transaction = (Transaction) cl.getAnnotation(Transaction.class);
        final String[] exclude = transaction.exclude();
        advice = (Advice) aspects.get("transaction");
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                String name = method.getName();
                if(name.equals("toString")||name.equals("equals")||name.equals("hashCode")){
                    return method.invoke(target, objects);
                }
                for (String s : exclude) {
                    if (name.equals(s)) {
                        return method.invoke(target, objects);
                    }
                }
                Signature signature = new Signature(name, method.getGenericReturnType(), method.getParameterTypes());
                JoinPoint joinPoint = new JoinPoint(target, signature, objects, this);
                advice.before(joinPoint);
                Object invoke = null;
                try {
                    invoke = method.invoke(target, objects);
                    advice.after(joinPoint);
                } catch (Exception e) {
                    advice.afterThrowing(joinPoint,e);
                }
                return advice.afterReturning(joinPoint, invoke);
            }
        };
        return Proxy.newProxyInstance(cl.getClassLoader(), cl.getInterfaces(),invocationHandler);
    }
}
