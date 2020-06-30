package edu.gdpu.myssm.spring.handler;

import edu.gdpu.myssm.spring.DefaultBeanFactory;
import edu.gdpu.myssm.spring.ProxyBeanFactory;
import edu.gdpu.myssm.spring.AnnotationHandler;
import edu.gdpu.myssm.spring.annotation.Aspect;
import edu.gdpu.myssm.spring.aop.Advice;
import edu.gdpu.myssm.spring.exception.UnImplementedException;
import edu.gdpu.myssm.utils.ReflectUtils;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 14:58:29
 */
public class AspectHandler implements AnnotationHandler {
    @Override
    public Object handle(Object o) {
        Class cl = (Class)o;
        try {
            if(!ReflectUtils.isImplemented(cl,Advice.class)){
                throw new UnImplementedException("未实现Advice接口");
            }else {
                Aspect aspect = (Aspect) cl.getAnnotation(Aspect.class);
                if(aspect.value().equals("")){
                    ProxyBeanFactory.getAspects().put(cl.getSimpleName().toLowerCase(), DefaultBeanFactory.getBean(cl));
                }else {
                    ProxyBeanFactory.getAspects().put(aspect.value(), DefaultBeanFactory.getBean(cl));
                }
            }
        } catch (UnImplementedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
