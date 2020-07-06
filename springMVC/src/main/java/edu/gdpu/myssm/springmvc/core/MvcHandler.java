package edu.gdpu.myssm.springmvc.core;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 嘿 林梓鸿
 */
public interface MvcHandler {

    void handler(HttpServletRequest request, HttpServletResponse response ) throws IOException;
}
