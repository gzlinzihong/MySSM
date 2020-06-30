package edu.gdpu.myssm.spring;

import edu.gdpu.myssm.spring.AnnotationHandler;
import edu.gdpu.myssm.utils.ResourceUtils;

import java.io.*;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月19日 18:42:01
 */
public class AnnotationHandlerFactory {
    private static Map<String,AnnotationHandler> handlers = new ConcurrentHashMap<>();

    static {
        List<InputStream> inputStreams = ResourceUtils.loadFiles("handlers.txt");
        for(InputStream ins:inputStreams){
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
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
            }finally {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
