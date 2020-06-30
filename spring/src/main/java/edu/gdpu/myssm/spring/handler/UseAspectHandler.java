package edu.gdpu.myssm.spring.handler;

import edu.gdpu.myssm.spring.ProxyBeanFactory;
import edu.gdpu.myssm.spring.AnnotationHandler;

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
