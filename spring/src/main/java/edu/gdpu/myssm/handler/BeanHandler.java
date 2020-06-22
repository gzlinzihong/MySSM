package edu.gdpu.myssm.handler;

import edu.gdpu.myssm.AnnotationHandler;
import edu.gdpu.myssm.ApplicationContext;
import edu.gdpu.myssm.annotation.Bean;
import edu.gdpu.myssm.utils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月20日 14:15:30
 */
public class BeanHandler implements AnnotationHandler {
    private Object invoker;


    public void setInvoker(Object invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object handle(Object o) {
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        Method method = (Method)o;
        Bean bean = method.getAnnotation(Bean.class);
        String value = bean.value();
        try {
            if(value.equals("")){
                BeanUtils.putBean(method.getName().toLowerCase(),method.invoke(invoker));
            }else {
                BeanUtils.putBean(value,method.invoke(invoker));
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }
}
