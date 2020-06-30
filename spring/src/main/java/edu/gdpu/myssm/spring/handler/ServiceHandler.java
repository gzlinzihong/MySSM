package edu.gdpu.myssm.spring.handler;

import edu.gdpu.myssm.spring.DefaultBeanFactory;
import edu.gdpu.myssm.spring.AnnotationHandler;
import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.annotation.Service;
import edu.gdpu.myssm.utils.BeanUtils;

import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月21日 13:10:50
 */
public class ServiceHandler implements AnnotationHandler {
    @Override
    public Object handle(Object o) {
        Class cl = (Class) o;
        return register(cl);
    }

    @SuppressWarnings("unchecked")
    private Object register(Class cl){
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        Object o = null;
        Service annotation = (Service) cl.getAnnotation(Service.class);
        try {
            o = DefaultBeanFactory.getBean(cl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(annotation.value().equals("")){
            String s = cl.getSimpleName().toLowerCase();
            if(s.endsWith("impl")){
                BeanUtils.putBean(s.split("impl")[0],o);
            }else {
                BeanUtils.putBean(s,o);
            }
        }else {
            BeanUtils.putBean(annotation.value(),o);
        }
        return o;
    }
}
