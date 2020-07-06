package edu.gdpu.myssm.springmvc.core;

import edu.gdpu.myssm.spring.annotation.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月05日 18:34:33
 */
@Component
public class HandlerMapping {
    private Map<String,String> getMapping = new HashMap<>();
    private Map<String,String> postMapping = new HashMap<>();


    private HandlerFactory handlerFactory = new HandlerFactory();

    public void setGetMapping(Map<String, String> getMapping) {
        this.getMapping = getMapping;
    }

    public void setPostMapping(Map<String, String> postMapping) {
        this.postMapping = postMapping;
    }

    public Handler getGetHandler(String uri,HttpServletRequest request,HttpServletResponse response){
        if(getMapping.containsKey(uri)){
            String s = getMapping.get(uri);
            String[] split = s.split("\\.");
            int index = s.lastIndexOf(".");
            String methodName = s.substring(index+1);
            String className = s.substring(0, index);
            try {
                Class<?> aClass = Class.forName(className);
                Method method = getMethodByName(aClass.getDeclaredMethods(),methodName);
                return handlerFactory.getHandler(aClass,method,request,response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Handler getPostHandler(String uri,HttpServletRequest request,HttpServletResponse response){
        if(postMapping.containsKey(uri)){
            String s = postMapping.get(uri);
            String[] split = s.split("\\.");
            int index = s.lastIndexOf(".");
            String methodName = s.substring(index+1);
            String className = s.substring(0, index);
            try {
                Class<?> aClass = Class.forName(className);
                Method method = getMethodByName(aClass.getDeclaredMethods(),methodName);
                return handlerFactory.getHandler(aClass,method,request,response);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private Method getMethodByName(Method[] methods,String name){
        for(Method method:methods){
            if(method.getName().equals(name)){
                return method;
            }
        }
        return null;
    }


}
