package edu.gdpu.myssm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月19日 17:58:59
 */
public class ApplicationContext {

    private static volatile ApplicationContext applicationContext ;

    private Map<String,Object> beans = new ConcurrentHashMap<>();

    private ApplicationContext(){

    }

    public Map<String, Object> getBeans() {
        return beans;
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
