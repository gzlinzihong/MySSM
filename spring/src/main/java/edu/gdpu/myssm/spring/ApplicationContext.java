package edu.gdpu.myssm.spring;

import edu.gdpu.myssm.utils.BeanUtils;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月19日 17:58:59
 */
public class ApplicationContext {


    private static volatile ApplicationContext applicationContext ;

    private ServletContext servletContext;

    private Map<String,Object> beans = new ConcurrentHashMap<>();

    private Map<Class,String> mapping = new ConcurrentHashMap<>();

    private ApplicationContext(){

    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        BeanUtils.putBean("servletContext",servletContext);
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public Map<String, Object> getBeans() {
        return beans;
    }

    public Map<Class, String> getMapping() {
        return mapping;
    }


    @SuppressWarnings("unchecked")
    public <T> T getBean(String name){
        return (T) beans.get(name);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type){
        return (T) beans.get(mapping.get(type));
    }


    public static ApplicationContext getApplicationContext(){
        if(applicationContext==null){
            synchronized (ApplicationContext.class){
                if(applicationContext==null){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    applicationContext = new ApplicationContext();
                }
            }
        }
        return applicationContext;
    }


}
