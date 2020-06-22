package edu.gdpu.myssm.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * properties工具类
 *
 * @author 嘿 林梓鸿
 * @date 2020年 06月18日 17:15:29
 * @version 1.0
 */
public class PropertiesUtils {

    private static final String SUFFIX = ".properties";

    public static Properties load(String fileName){
        if(fileName.endsWith(SUFFIX)){
            return doLoad(fileName,true);
        }else {
            return doLoad(fileName+SUFFIX,true);
        }
    }

    public static Properties load(String fileName,boolean inClassPath){
        if(fileName.endsWith(SUFFIX)){
            if(inClassPath){
                return doLoad(fileName,true);
            }else {
                return doLoad(fileName,false);
            }
        }else {
            if(inClassPath){
                return doLoad(fileName+SUFFIX,true);
            }else {
                return doLoad(fileName+SUFFIX,false);
            }
        }
    }

    private static Properties doLoad(String fileName,boolean inClassPath){
        Properties prop = new Properties();
        if(inClassPath){
            try {
                prop.load(ResourceUtils.loadInClassPath(fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return prop;
    }
}
