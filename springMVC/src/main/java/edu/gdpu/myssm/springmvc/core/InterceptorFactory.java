package edu.gdpu.myssm.springmvc.core;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.List;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月06日 13:30:48
 */
public class InterceptorFactory {

    public static Filter getInterceptor(final Interceptor interceptor, final String[] exclude){

        return (Filter) Proxy.newProxyInstance(interceptor.getClass().getClassLoader(), interceptor.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if(method.getName().equals("doFilter")){
                    HttpServletRequest request = (HttpServletRequest) objects[0];
                    String uri = request.getRequestURI();
                    // /webjars/*
                    // /webjars/
                    HttpServletResponse response = (HttpServletResponse) objects[1];
                    FilterChain filterChain = (FilterChain) objects[2];

                    if(StaticResourceHandler.isResource(uri)){
                        filterChain.doFilter(request,response);
                        return null;
                    }

                    if(exclude!=null){
                        for(String s:exclude){
                            int i = s.lastIndexOf("*");
                            if(i==-1){
                                if(s.equals(uri)){
                                    filterChain.doFilter(request,response);
                                    return null;
                                }
                            }else {
                                if(uri.startsWith(uri.substring(0, i))){
                                    filterChain.doFilter(request,response);
                                    return null;
                                }
                            }
                        }
                    }
                    interceptor.doPre(request,response,filterChain);
                    interceptor.doAfter(request,response,filterChain);
                    return null;
                }else {
                    return method.invoke(interceptor, objects);
                }
            }
        });
    }

}
