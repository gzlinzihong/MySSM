package edu.gdpu.myssm.springmvc.core;

import edu.gdpu.myssm.spring.annotation.Component;
import edu.gdpu.myssm.utils.PropertiesUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月04日 14:37:42
 */
@Component
public class StaticResourceHandler implements MvcHandler {

    public Map<String,String> mimeMapping = new HashMap<>();

     {
         Properties prop = PropertiesUtils.load("mime.properties");
         Set<Object> key = prop.keySet();
         String s = "";
         for(Object o:key){
             s = (String)o;
             mimeMapping.put(s,prop.getProperty(s));
         }
    }

    @Override
    public void handler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] split = uri.split("\\.");
        String extension = split[split.length - 1];
        response.setContentType(mimeMapping.get(extension));
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream("static" + uri);
        if(ins == null){

        }
        byte[] bytes = new byte[ins.available()];
        int a = 0;
        ServletOutputStream outputStream = response.getOutputStream();
        while ((a=ins.read(bytes))!=-1){
            outputStream.write(bytes);
        }
    }

    public static boolean isResource(String uri){
        URL resource = StaticResourceHandler.class.getClassLoader().getResource("static" + uri);
        return resource!=null;
    }
}
