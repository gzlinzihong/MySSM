package edu.gdpu.myssm.spring.handler;

import edu.gdpu.myssm.spring.AnnotationHandler;
import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.annotation.AutoWired;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月21日 13:23:14
 */
public class AutoWiredHandler implements AnnotationHandler {


    private Object invoker;

    public void setInvoker(Object invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object handle(Object o) {
        Field[] fields = (Field[])o;
        for(Field field:fields){
            if(field.isAnnotationPresent(AutoWired.class)){
                inject(field);
            }
        }
        return null;
    }

    private void inject(Field field){
        ApplicationContext applicationContext = ApplicationContext.getApplicationContext();
        Map<String, Object> beans = applicationContext.getBeans();
        field.setAccessible(true);
        AutoWired autoWired = field.getAnnotation(AutoWired.class);
        try {
            Object bean = null;
            try {
                 bean = applicationContext.getBean(field.getType());
            }catch (NullPointerException e){

            }
            if(bean!=null){
                field.set(invoker,bean);
            } else {
                //尝试类型匹配
                if(autoWired.value().equals("")){
                    field.set(invoker,beans.get(field.getName().toLowerCase()));
                }else {
                    field.set(invoker,beans.get(autoWired.value()));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
