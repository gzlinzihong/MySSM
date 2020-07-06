package edu.gdpu.myssm.springmvc.core;

import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.springmvc.utils.JavassistUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月05日 18:40:49
 */
public class HandlerFactory {


    public Handler getHandler(Class cl, Method method, HttpServletRequest request, HttpServletResponse response){
        Class<?>[] parameterTypes = method.getParameterTypes();
        Parameter[] parameters = method.getParameters();
        String[] paramNames = JavassistUtils.getParameterNames(cl,method);
        Object[] args = new Object[parameterTypes.length];
        for(int i = 0;i<parameterTypes.length;i++){
            args[i] = getObject(parameterTypes[i],request,response,paramNames[i]);
        }
        Object bean = ApplicationContext.getApplicationContext().getBean(cl);
        return new HandlerAdapter(bean,method,args);
    }

    private Object getObject(Class c,HttpServletRequest request,HttpServletResponse response,String paramName){
        if(c == HttpSession.class){
            return request.getSession();
        }else if(c == HttpServletRequest.class){
            return request;
        }else if(c == HttpServletResponse.class){
            return response;
        }else {
            if(isUserDefined(c)){
                return wrap(c,request);
            }else {
                if(c == int.class || c==Integer.class){
                    return Integer.valueOf(request.getParameter(paramName));
                }else {
                    return request.getParameter(paramName);
                }
            }
        }
    }

    private boolean isUserDefined(Class cl){
        return !(cl.getClassLoader() == null);
    }

    private Object wrap(Class c,HttpServletRequest request){
        Object instance = null;
        try {
            instance = c.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Field[] fields = c.getDeclaredFields();
        try {
            for(Field field:fields){
                field.setAccessible(true);
                Class<?> type = field.getType();
                String parameter = request.getParameter(field.getName());
                if(parameter!=null){
                    if(type ==int.class || type==Integer.class){
                        field.set(instance,Integer.valueOf(parameter));
                    }else if (type == String.class){
                        field.set(instance, parameter);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }


}
