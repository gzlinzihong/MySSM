package edu.gdpu.myssm.springmvc.handler;

import edu.gdpu.myssm.spring.AnnotationHandler;
import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.DefaultBeanFactory;
import edu.gdpu.myssm.springmvc.annotation.Controller;
import edu.gdpu.myssm.springmvc.annotation.GetMapping;
import edu.gdpu.myssm.springmvc.annotation.PostMapping;
import edu.gdpu.myssm.springmvc.exception.PathBoundException;
import edu.gdpu.myssm.utils.BeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月05日 16:40:58
 */
public class ControllerHandler implements AnnotationHandler {

    private Map<String,String> getMapping = new HashMap<>();
    private Map<String,String> postMapping = new HashMap<>();


    @Override
    public Object handle(Object o) {
        Class cl = (Class)o;
        Object register = register(cl);
        registerMapping(cl);
        return register;
    }

    private Object register(Class cl){
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        Object o = null;
        Controller annotation = (Controller) cl.getAnnotation(Controller.class);
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

    private void registerMapping(Class cl){
        try {
            for(Method method:cl.getDeclaredMethods()){
                if(method.isAnnotationPresent(GetMapping.class)){
                    put(method,method.getAnnotation(GetMapping.class));
                }else if(method.isAnnotationPresent(PostMapping.class)){
                    put(method,method.getAnnotation(PostMapping.class));
                }
            }
        } catch (PathBoundException e) {
            e.printStackTrace();
        }
    }

    private void put(Method method, Annotation annotation) throws PathBoundException {
        if(annotation instanceof GetMapping){
            GetMapping get = (GetMapping) annotation;
            String value = get.value();
            if(getMapping.containsKey(value)){
                throw new PathBoundException("路径已被绑定");
            }else {
                getMapping.put(value,method.getDeclaringClass().getName()+"."+method.getName());
            }
        }

        if(annotation instanceof PostMapping){
            PostMapping post = (PostMapping) annotation;
            String value = post.value();
            if(postMapping.containsKey(value)){
                throw new PathBoundException("路径已被绑定");
            }else {
                postMapping.put(value,method.getDeclaringClass().getName()+"."+method.getName());
            }
        }
    }

    public Map<String, String> getGetMapping() {
        return getMapping;
    }

    public Map<String, String> getPostMapping() {
        return postMapping;
    }
}
