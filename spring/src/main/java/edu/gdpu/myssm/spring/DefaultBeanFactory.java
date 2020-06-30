package edu.gdpu.myssm.spring;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 14:25:12
 */
public class DefaultBeanFactory {


    private DefaultBeanFactory(){

    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> cl){
        try {
            if(cl.isInterface()){
                return (T) new InterfaceObject(cl);
            }
            return cl.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
