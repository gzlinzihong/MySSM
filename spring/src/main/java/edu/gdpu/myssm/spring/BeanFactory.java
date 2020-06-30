package edu.gdpu.myssm.spring;

/**
 * @author 嘿 林梓鸿
 */
public interface BeanFactory<T> {

    T getBean(T t);
}
