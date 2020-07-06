package edu.gdpu.myssm.springmvc.core;

import edu.gdpu.myssm.spring.annotation.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月05日 23:30:20
 */
@Component
public class ResourceResolver {
    private Map<String,String> viewMapping = new HashMap<>();


    public void addView(String path,String view){
        viewMapping.put(path,view);
    }


    public Map<String, String> getViewMapping() {
        return viewMapping;
    }

    public boolean contains(String uri){
        return viewMapping.containsKey(uri);
    }

    public String getView(String uri){
        if(contains(uri)){
            return viewMapping.get(uri);
        }
        return null;
    }

}
