package edu.gdpu.myssm.springmvc.core;

import edu.gdpu.myssm.spring.annotation.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月05日 23:27:23
 */
@Component
public class ViewResolver {

    private final String SUFFIX = ".html";

    private final String BASE = "templates/";

    private ClassLoader loader = Thread.currentThread().getContextClassLoader();

    public void resolve(String view, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        InputStream ins = loader.getResourceAsStream(BASE + view + SUFFIX);
        byte[] bytes = new byte[ins.available()];
        int a = 0;
        ServletOutputStream outputStream = response.getOutputStream();
        while ((a=ins.read(bytes))!=-1){
            outputStream.write(bytes);
        }
    }
}
