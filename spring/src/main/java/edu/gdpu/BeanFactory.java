package edu.gdpu;

import java.lang.reflect.InvocationTargetException;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 14:25:12
 */
public class BeanFactory {


    private BeanFactory(){

    }

    @SuppressWarnings("unchecked")
    public static Object getBean(Class cl){
        try {
            return cl.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
