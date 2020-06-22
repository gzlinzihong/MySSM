package edu.gdpu.myssm.handler;

import edu.gdpu.BeanFactory;
import edu.gdpu.ProxyBeanFactory;
import edu.gdpu.myssm.AnnotationHandler;
import edu.gdpu.myssm.annotation.Aspect;
import edu.gdpu.myssm.aop.Advice;
import edu.gdpu.myssm.exception.UnImplementedException;
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
                    ProxyBeanFactory.getAspects().put(cl.getSimpleName().toLowerCase(),BeanFactory.getBean(cl));
                }else {
                    ProxyBeanFactory.getAspects().put(aspect.value(),BeanFactory.getBean(cl));
                }
            }
        } catch (UnImplementedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
