package edu.gdpu.myssm.spring;

import edu.gdpu.myssm.spring.annotation.Transaction;
import edu.gdpu.myssm.spring.annotation.UseAspect;
import edu.gdpu.myssm.spring.handler.AutoWiredHandler;
import edu.gdpu.myssm.utils.PackageScanUtils;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.annotation.HandlesTypes;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        classes.addAll(PackageScanUtils.scanPackage("edu.gdpu"));



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

        TomcatConfig tomcatConfig = ApplicationContext.getApplicationContext().getBean(TomcatConfig.class);
        Tomcat tomcat = new Tomcat();


        //看源码发现,他只能设置一个service,直接拿默认的
        Service service = tomcat.getService();

        //创建连接器,并且添加对应的连接器,同时连接器指定端口
        Connector connector = new Connector();
        connector.setPort(tomcatConfig.getPort());
        service.addConnector(connector);

        //创建一个引擎,放入service中
        Engine engine = new StandardEngine();
        service.setContainer(engine);
        engine.setDefaultHost("localhost");
        engine.setName("myTomcat");

        //添加host
        Host host = new StandardHost();
        engine.addChild(host);
        host.setName("localhost");
        host.setAppBase("webapp");

        //在对应的host下面创建一个 context 并制定他的工作路径,会加载该目录下的所有class文件,或者静态文件
        StandardContext context = (StandardContext) tomcat.addContext(host, tomcatConfig.getContextPath(),System.getProperty("user.dir"));
        //初始化ContextConfig配置
        context.addLifecycleListener(new ContextConfig());

//                //创建一个servlet
//        Wrapper dispatcherServlet = new StandardWrapper();
//        dispatcherServlet.setServlet(new DispatcherServlet());
//        dispatcherServlet.setName("dispatcherServlet");
//        //把servlet加入到contxt中
//        context.addChild(dispatcherServlet);
////这里注意,要先添加到contxt中在映射路径,不然会空指针
//        dispatcherServlet.addMapping("/**");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }


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


        //使其处于阻塞状态一直等待客户端连接 否则启动完就结束了
        tomcat.getServer().await();
    }
}
