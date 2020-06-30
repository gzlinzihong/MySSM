package edu.gdpu.myssm.spring;

import edu.gdpu.myssm.spring.ApplicationInitializer;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月18日 20:51:41
 */
@HandlesTypes(ApplicationInitializer.class)
public class ServletInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        for(Class<?> c:set){
            try {
                Object o = c.getDeclaredConstructor().newInstance();
                ApplicationInitializer a = null;
                if(o instanceof ApplicationInitializer){
                    a = (ApplicationInitializer)o;
                    a.onStartup(servletContext);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
