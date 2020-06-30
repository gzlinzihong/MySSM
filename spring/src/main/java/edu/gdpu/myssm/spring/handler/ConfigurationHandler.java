package edu.gdpu.myssm.spring.handler;

import edu.gdpu.myssm.spring.AnnotationHandlerFactory;
import edu.gdpu.myssm.spring.DefaultBeanFactory;
import edu.gdpu.myssm.spring.AnnotationHandler;
import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.annotation.Bean;
import edu.gdpu.myssm.spring.annotation.Configuration;
import edu.gdpu.myssm.spring.exception.UnsupportedTypesException;
import edu.gdpu.myssm.utils.BeanUtils;
import edu.gdpu.myssm.utils.PropertiesUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月19日 18:49:04
 */
public class ConfigurationHandler implements AnnotationHandler {
    @Override
    public Object handle(Object o) {
        Class cl = (Class)o;
        Object register = register(cl);
        injectValue(cl,register);
        registerBean(cl.getDeclaredMethods(),register);
        return o;
    }


    private Object register(Class cl){
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        Configuration annotation = (Configuration)cl.getAnnotation(Configuration.class);
        Object o = null;
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

    private void registerBean(Method[] methods,Object o){
        BeanHandler beanHandler = (BeanHandler) AnnotationHandlerFactory.getHandler("BeanHandler");
        for(Method method:methods){
            if(!method.isAnnotationPresent(Bean.class)){
                continue;
            }
            beanHandler.setInvoker(o);
            beanHandler.handle(method);
        }
    }

    private void injectValue(Class cl,Object o){
        String fileName = getFileName(cl);
        try {
            if(!fileName.endsWith(".properties")){
                throw new UnsupportedTypesException("不支持的文件类型");
            }else {
                Properties prop = PropertiesUtils.load(fileName);
                doInject(prop,cl.getDeclaredFields(),o);
            }
        } catch (UnsupportedTypesException e) {
            e.printStackTrace();
        }
    }

    private String getFileName(Class cl){
        AnnotationHandler resourceHandler = AnnotationHandlerFactory.getHandler("ResourceHandler");
        String fileName = (String) resourceHandler.handle(cl);
        return fileName;
    }

    private void doInject(Properties prop,Field[] fields,Object o){
        AnnotationHandler valueHandler = AnnotationHandlerFactory.getHandler("ValueHandler");
        String key = null;
        for(Field field:fields){
            try {
                Object result = valueHandler.handle(field);
                if(result!=null){
                    key = (String)result;
                    Class<?> type = field.getType();
                    Object value = this.switchType(prop, type.getSimpleName(), key);
                    field.set(o,value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                try {
                    throw new UnsupportedTypesException("目前仅支持Integer和String");
                } catch (UnsupportedTypesException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private Object switchType(Properties prop,String className,String key){
        switch (className){
            case "Integer":return Integer.valueOf(prop.getProperty(key));
            case "String":return prop.getProperty(key);
        }
        return prop.get(key);
    }
}
