package edu.gdpu.myssm.handler;

import edu.gdpu.myssm.AnnotationHandler;
import edu.gdpu.myssm.annotation.Resource;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月21日 12:42:11
 */
public class ResourceHandler implements AnnotationHandler {

    @Override
    public Object handle(Object o) {
        Class cl = (Class)o;
        String fileName = null;
        if(cl.isAnnotationPresent(Resource.class)){
            Resource resource = (Resource) cl.getAnnotation(Resource.class);
            if(resource.value().equals("")){
                fileName = "application.properties";
            }else {
                fileName = resource.value();
            }
        }else {
            fileName = "application.properties";
        }
        return fileName;
    }
}
