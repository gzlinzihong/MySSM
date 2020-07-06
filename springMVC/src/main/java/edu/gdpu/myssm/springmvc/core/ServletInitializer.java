package edu.gdpu.myssm.springmvc.core;

import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.ApplicationInitializer;
import edu.gdpu.myssm.spring.TomcatConfig;
import edu.gdpu.myssm.springmvc.core.InterceptorRegistry;
import edu.gdpu.myssm.springmvc.core.ResourceResolver;
import edu.gdpu.myssm.springmvc.core.WebConfigureSupport;
import edu.gdpu.myssm.springmvc.servlet.DispatcherServlet;
import edu.gdpu.myssm.utils.BeanUtils;
import org.apache.catalina.servlets.DefaultServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.util.EnumSet;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月02日 17:53:45
 */
public class ServletInitializer implements ApplicationInitializer {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        DispatcherServlet dispatcherServlet = ApplicationContext.getApplicationContext().getBean(DispatcherServlet.class);
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
        dispatcher.setLoadOnStartup(1);
        TomcatConfig config = ApplicationContext.getApplicationContext().getBean(TomcatConfig.class);
        String contextPath = config.getContextPath();
        if(contextPath.equals("/")){
            dispatcher.addMapping("/*");
        }else {
            dispatcher.addMapping(contextPath+"/*");
        }
        BeanUtils.putBean("dispatcherServlet",dispatcherServlet);

        try {

            ApplicationContext applicationContext = ApplicationContext.getApplicationContext();
            WebConfigureSupport webConfig = applicationContext.getBean(WebConfigureSupport.class);
            if(webConfig!=null){
                InterceptorRegistry registry = applicationContext.getBean(InterceptorRegistry.class);
                registry.setContext(servletContext);
                ResourceResolver resourceResolver = applicationContext.getBean(ResourceResolver.class);
                webConfig.filterResolve(registry);
                webConfig.resourceResolve(resourceResolver);

                registry.doRegister();
            }
        }catch (NullPointerException e){
            logger.warn("you do not register a WebConfig");
        }
    }
}
