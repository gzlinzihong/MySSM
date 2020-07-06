package edu.gdpu.myssm.utils;

import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.InterfaceObject;
import edu.gdpu.myssm.spring.exception.KeyExistedException;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月20日 14:48:40
 */
public class BeanUtils {

    public static Object putBean(String key,Object value){
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        Map<Class, String> mapping = ApplicationContext.getApplicationContext().getMapping();
        if(beans.containsKey(key)){
            if(!(beans.get(key) instanceof InterfaceObject)){
                try {
                    throw new KeyExistedException("该bean已存在");
                } catch (KeyExistedException e) {
                    e.printStackTrace();
                }
            }else {
                InterfaceObject interfaceObject = (InterfaceObject) beans.get(key);
                mapping.put(interfaceObject.getaClass(),key);
                return beans.put(key,value);
            }
        }else {
            mapping.put(value.getClass(),key);
            if(value instanceof ServletContext){
                mapping.put(ServletContext.class,key);
            }
            return beans.put(key,value);
        }
        mapping.put(value.getClass(),key);
        return beans.put(key,value);
    }
}
