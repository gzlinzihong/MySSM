package edu.gdpu.myssm.springmvc.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月05日 18:45:34
 */
public class HandlerAdapter implements Handler {

    private Object invoker;

    private Method method;

    private Object[] args;

    public HandlerAdapter(Object invoker, Method method, Object[] args) {
        this.invoker = invoker;
        this.method = method;
        this.args = args;
    }

    @Override
    public Object handle() {
        try {
            return method.invoke(invoker,args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Method getMethod() {
        return method;
    }
}
