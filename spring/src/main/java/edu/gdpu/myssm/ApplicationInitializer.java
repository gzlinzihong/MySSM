package edu.gdpu.myssm;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author 嘿 林梓鸿
 */
public interface ApplicationInitializer  {
    /**
     * 启动则加载方法
     * @param servletContext
     * @throws ServletException
     */
    void onStartup(ServletContext servletContext) throws ServletException;
}
