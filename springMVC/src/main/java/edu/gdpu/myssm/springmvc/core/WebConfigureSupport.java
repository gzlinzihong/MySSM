package edu.gdpu.myssm.springmvc.core;

/**
 * @author 嘿 林梓鸿
 */
public abstract class WebConfigureSupport {


    public abstract void resourceResolve(ResourceResolver resolver);

    public abstract void filterResolve(InterceptorRegistry registry);

}
