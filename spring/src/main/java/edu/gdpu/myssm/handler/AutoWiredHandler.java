package edu.gdpu.myssm.handler;

import edu.gdpu.myssm.AnnotationHandler;
import edu.gdpu.myssm.ApplicationContext;
import edu.gdpu.myssm.annotation.AutoWired;

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
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        field.setAccessible(true);
        AutoWired autoWired = field.getAnnotation(AutoWired.class);
        try {
            if(autoWired.value().equals("")){
                field.set(invoker,beans.get(field.getName().toLowerCase()));
            }else {
                field.set(invoker,beans.get(autoWired.value()));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
