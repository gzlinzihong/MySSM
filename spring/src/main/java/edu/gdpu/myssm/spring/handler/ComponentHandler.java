package edu.gdpu.myssm.spring.handler;

import edu.gdpu.myssm.spring.DefaultBeanFactory;
import edu.gdpu.myssm.spring.AnnotationHandler;
import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.annotation.Component;
import edu.gdpu.myssm.utils.BeanUtils;

import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月21日 13:10:50
 */
public class ComponentHandler implements AnnotationHandler {
    @Override
    public Object handle(Object o) {
        Class cl = (Class) o;
//        if(cl.isAnnotationPresent(UseAspect.class)){
//            return registerProxy(cl);
//        }else {
//            return register(cl);
//        }
        return register(cl);
    }

    private Object register(Class cl){
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        Object o = null;
        Component annotation = (Component) cl.getAnnotation(Component.class);
        try {
            o = DefaultBeanFactory.getBean(cl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(annotation.value().equals("")){
            BeanUtils.putBean(cl.getSimpleName().toLowerCase(),o);
        }else {
            BeanUtils.putBean(annotation.value(),o);
        }
        return o;
    }

//    private Object registerProxy(Class cl){
//        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
//        Object o = null;
//        Component annotation = (Component) cl.getAnnotation(Component.class);
//        try {
//            o = ProxyBeanFactory.getBean(cl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if(annotation.value().equals("")){
//            BeanUtils.putBean(cl.getSimpleName().toLowerCase(),o);
//        }else {
//            BeanUtils.putBean(annotation.value(),o);
//        }
//        return o;
//    }

}
