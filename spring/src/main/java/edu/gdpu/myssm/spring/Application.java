package edu.gdpu.myssm.spring;

import edu.gdpu.myssm.spring.annotation.Transaction;
import edu.gdpu.myssm.spring.annotation.UseAspect;
import edu.gdpu.myssm.spring.handler.AutoWiredHandler;
import edu.gdpu.myssm.utils.PackageScanUtils;

import javax.servlet.annotation.HandlesTypes;
import java.lang.annotation.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月18日 20:54:10
 */
public class Application {

    public static void run(Class c){

        //1.准备好applicationContext容器
        new BootLogo().display();
        ApplicationContext.getApplicationContext();
        ListenerStarter listenerStarter = new ListenerStarter();
        listenerStarter.begin();

        //2.扫描包路径获得所有class
        Set<Class> classes = PackageScanUtils.scanPackage(c.getPackage().getName());


        //3.扫描注解
        for(Class cl:classes){
            Annotation[] annotations = cl.getAnnotations();
            for(Annotation a:annotations){
                if(a instanceof Target ||
                        a instanceof Retention ||
                        a instanceof Documented ||
                        a instanceof Inherited ||
                        a instanceof HandlesTypes||
                        a instanceof UseAspect||
                        a instanceof Transaction){
                    continue;
                }
                AnnotationHandler handler = AnnotationHandlerFactory.getHandler(a);
                if(handler != null){
                    handler.handle(cl);
                }
            }
        }

        listenerStarter.completeScan();



        AutoWiredHandler autoWiredHandler = (AutoWiredHandler)AnnotationHandlerFactory.getHandler("AutoWiredHandler");
        Map<String, Object> beans = ApplicationContext.getApplicationContext().getBeans();
        Set<String> keys = beans.keySet();
        AnnotationHandler useAspectHandler = AnnotationHandlerFactory.getHandler("UseAspectHandler");
        AnnotationHandler transactionHandler = AnnotationHandlerFactory.getHandler("TransactionHandler");
        Object o = null;
        for(String s:keys){
             o = beans.get(s);
            if(o.getClass().isAnnotationPresent(Transaction.class)){
                beans.put(s,transactionHandler.handle(o));
            }
            if(o.getClass().isAnnotationPresent(UseAspect.class)){
                beans.put(s,useAspectHandler.handle(o));
            }
            autoWiredHandler.setInvoker(o);
            autoWiredHandler.handle(o.getClass().getDeclaredFields());
        }

        listenerStarter.prepared();

        listenerStarter.start();


//        File base = new File("/");
//        Tomcat tomcat = new Tomcat();
//
//        //设置端口
//        tomcat.setPort(8080);
//
//        tomcat.addWebapp("/",base.getAbsolutePath());
//        try {
//            tomcat.start();
//            //使其处于阻塞状态一直等待客户端连接 否则启动完就结束了
//            tomcat.getServer().await();
//        } catch (LifecycleException e) {
//            e.printStackTrace();
//        }
    }
}
