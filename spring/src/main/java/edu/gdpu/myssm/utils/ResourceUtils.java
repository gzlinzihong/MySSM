package edu.gdpu.myssm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 获取资源文件工具类
 *
 * @author 嘿 林梓鸿
 * @date 2020年 06月18日 17:22:11
 */
public class ResourceUtils {


    public static InputStream loadInClassPath(String fileName){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }


    public static List<InputStream> loadFiles(String fileName){
        List<InputStream> ins = new ArrayList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> resources = loader.getResources(fileName);
            while (resources.hasMoreElements()){
                ins.add(resources.nextElement().openStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ins;
    }

}

