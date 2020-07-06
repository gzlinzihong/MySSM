package edu.gdpu.myssm.springmvc.core;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月06日 19:11:10
 */
public class EncodingInterceptor implements Interceptor {
    @Override
    public void doPre(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        filterChain.doFilter(request,response);
    }

    @Override
    public void doAfter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
    }
}
