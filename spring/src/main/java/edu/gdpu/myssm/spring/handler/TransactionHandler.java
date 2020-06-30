package edu.gdpu.myssm.spring.handler;

import edu.gdpu.myssm.spring.AnnotationHandler;
import edu.gdpu.myssm.spring.ProxyBeanFactory;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月30日 13:52:23
 */
public class TransactionHandler implements AnnotationHandler {
    @Override
    public Object handle(Object o) {
        return ProxyBeanFactory.getTransactionBean(o);
    }
}
