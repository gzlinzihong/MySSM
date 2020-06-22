package edu.gdpu.myssm.handler;

import edu.gdpu.BeanFactory;
import edu.gdpu.myssm.AnnotationHandler;
import edu.gdpu.myssm.ApplicationContext;
import edu.gdpu.myssm.annotation.Component;
import edu.gdpu.myssm.annotation.Controller;
import edu.gdpu.myssm.utils.BeanUtils;

import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月21日 13:10:50
 */
public class ControllerHandler implements AnnotationHandler {
    @Override
    public Object handle(Object o) {
        Class cl = (Class) o;
        return register(cl);
    }

    private Object register(Class cl){
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        Object o = null;
        Controller annotation = (Controller) cl.getAnnotation(Controller.class);
        try {
            o = BeanFactory.getBean(cl);
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

}
