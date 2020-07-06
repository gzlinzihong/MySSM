package edu.gdpu.myssm.springmvc.servlet;

import edu.gdpu.myssm.spring.annotation.AutoWired;
import edu.gdpu.myssm.spring.annotation.Component;
import edu.gdpu.myssm.springmvc.annotation.ResponseBody;
import edu.gdpu.myssm.springmvc.core.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月02日 17:34:03
 */
@Component
public class DispatcherServlet extends HttpServlet {

    @AutoWired
    private ServletContext context;


    @AutoWired
    private StaticResourceHandler staticResourceHandler;

    @AutoWired
    private HandlerMapping handlerMapping;

    @AutoWired
    private ViewResolver viewResolver;

    @AutoWired
    private ResourceResolver resourceResolver;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Handler handler = null;
        if(uri.contains(".")){
            staticResourceHandler.handler(req,resp);
        }else {
            String view = resourceResolver.getView(uri);
            if(view!=null){
                viewResolver.resolve(view,resp);
                return;
            }
            String method = req.getMethod();
            if(method.equals("GET")){
                handler = handlerMapping.getGetHandler(uri, req, resp);
            }else if(method.equals("POST")){
                handler = handlerMapping.getPostHandler(uri,req,resp);
            }
            Object result = handler.handle();
            if(handler.getMethod().isAnnotationPresent(ResponseBody.class)){
                resp.getWriter().write((String) result);
            }else {
                viewResolver.resolve((String)result,resp);
            }
        }
    }
}
