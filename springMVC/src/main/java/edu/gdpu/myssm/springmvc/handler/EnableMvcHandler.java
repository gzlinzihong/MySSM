package edu.gdpu.myssm.springmvc.handler;

import edu.gdpu.myssm.spring.AnnotationHandler;
import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.annotation.Configuration;
import edu.gdpu.myssm.springmvc.core.WebConfigureSupport;

import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月06日 13:12:00
 */
public class EnableMvcHandler implements AnnotationHandler {
    @Override
    public Object handle(Object o) {
        Class cl = (Class)o;
        Class superclass = cl.getSuperclass();
        if(superclass!= WebConfigureSupport.class){
            throw new IllegalArgumentException(cl.getName()+"未继承WebConfigure类");
        }
        register(cl);
        return o;
    }

    private void register(Class cl){
        Map<Class, String> mapping = ApplicationContext.getApplicationContext().getMapping();
        if(!cl.isAnnotationPresent(Configuration.class)){
            throw new IllegalArgumentException(cl.getName()+"未用@Configuration标注");
        }
        Configuration annotation = (Configuration)cl.getAnnotation(Configuration.class);
        if(annotation.value().equals("")){
            mapping.put(cl.getSuperclass(),cl.getSimpleName().toLowerCase());
        }else {
            mapping.put(cl.getSuperclass(),annotation.value());
        }
    }
}
