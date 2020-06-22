package edu.gdpu;

import edu.gdpu.myssm.AnnotationHandler;
import edu.gdpu.myssm.annotation.Configuration;
import edu.gdpu.myssm.handler.ConfigurationHandler;
import edu.gdpu.myssm.utils.ResourceUtils;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月19日 18:42:01
 */
public class AnnotationHandlerFactory {
    private static Map<String,AnnotationHandler> handlers = new ConcurrentHashMap<>();

    static {
        InputStream inputStream = ResourceUtils.loadInClassPath("handlers.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String s = null;
            while ((s=reader.readLine())!=null){
                Class<?> aClass = Class.forName(s);
                AnnotationHandler o = (AnnotationHandler) aClass.getDeclaredConstructor().newInstance();
                String[] split = s.split("\\.");
                handlers.put(split[split.length-1],o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AnnotationHandler getHandler(Annotation annotation){
        String key = annotation.annotationType().getSimpleName()+"Handler";
        return handlers.containsKey(key)?handlers.get(key):null;
    }

    public static AnnotationHandler getHandler(String name){
        return handlers.containsKey(name)?handlers.get(name):null;
    }
}
