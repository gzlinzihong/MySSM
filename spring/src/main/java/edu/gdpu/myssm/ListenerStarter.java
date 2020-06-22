package edu.gdpu.myssm;

import edu.gdpu.myssm.utils.ResourceUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 23:17:49
 */
public class ListenerStarter implements ApplicationListener {
    private static List<ApplicationListener> listeners = new ArrayList<>();

    static {
        InputStream inputStream = ResourceUtils.loadInClassPath("listener.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String s = null;
            while ((s=reader.readLine())!=null){
                Class<?> aClass = Class.forName(s);
                ApplicationListener o = (ApplicationListener) aClass.getDeclaredConstructor().newInstance();
                listeners.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void begin() {
        for(ApplicationListener ap:listeners){
            ap.begin();
        }
    }

    @Override
    public void completeScan() {
        for(ApplicationListener ap:listeners){
            ap.completeScan();
        }
    }


    @Override
    public void prepared() {
        for(ApplicationListener ap:listeners){
            ap.prepared();
        }
    }

    @Override
    public void start() {
        for(ApplicationListener ap:listeners){
            ap.start();
        }
    }
}
