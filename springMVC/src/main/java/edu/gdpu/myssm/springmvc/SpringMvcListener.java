package edu.gdpu.myssm.springmvc;

import edu.gdpu.myssm.spring.*;

import edu.gdpu.myssm.spring.handler.AutoWiredHandler;
import edu.gdpu.myssm.springmvc.core.*;
import edu.gdpu.myssm.springmvc.handler.ControllerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月05日 16:36:28
 */
public class SpringMvcListener implements ApplicationListener {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void begin() {

    }

    @Override
    public void completeScan() {
        ControllerHandler controller = (ControllerHandler) AnnotationHandlerFactory.getHandler("ControllerHandler");
        HandlerMapping handlerMapping = ApplicationContext.getApplicationContext().getBean(HandlerMapping.class);
        handlerMapping.setGetMapping(controller.getGetMapping());
        handlerMapping.setPostMapping(controller.getPostMapping());

        AutoWiredHandler autoWiredHandler = (AutoWiredHandler) AnnotationHandlerFactory.getHandler("AutoWiredHandler");
        WebConfigureSupport webConfig = ApplicationContext.getApplicationContext().getBean(WebConfigureSupport.class);
        if(webConfig!=null){
            autoWiredHandler.setInvoker(webConfig);
            autoWiredHandler.handle(webConfig.getClass().getDeclaredFields());
        }
        logger.info("path bound successfully");
    }

    @Override
    public void prepared() {
    }

    @Override
    public void start() {

    }
}
