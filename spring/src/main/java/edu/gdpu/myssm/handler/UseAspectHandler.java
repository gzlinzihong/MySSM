package edu.gdpu.myssm.handler;

import edu.gdpu.ProxyBeanFactory;
import edu.gdpu.myssm.AnnotationHandler;
import edu.gdpu.myssm.annotation.UseAspect;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 15:27:54
 */
public class UseAspectHandler implements AnnotationHandler {
    @Override
    public Object handle(Object o) {
        return ProxyBeanFactory.getBean(o);
    }
}
