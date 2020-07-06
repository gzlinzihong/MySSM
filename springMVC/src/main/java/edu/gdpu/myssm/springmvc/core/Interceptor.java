package edu.gdpu.myssm.springmvc.core;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 嘿 林梓鸿
 */
public interface Interceptor extends Filter {

    void doPre(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;


    void doAfter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;

    @Override
    default void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException{}
}
