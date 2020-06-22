package edu.gdpu.myssm.utils;

import java.io.InputStream;

/**
 * 获取资源文件工具类
 *
 * @author 嘿 林梓鸿
 * @date 2020年 06月18日 17:22:11
 */
public class ResourceUtils {


    public static InputStream loadInClassPath(String fileName){
        return ResourceUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

}
