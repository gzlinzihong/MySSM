package edu.gdpu.myssm.utils;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 15:14:38
 */
public class ReflectUtils {

    public static boolean isImplemented(Class cl,Class face){
        String faceSimpleName = face.getSimpleName();
        for(Class c:cl.getInterfaces()){
            if(c.getSimpleName().equals(faceSimpleName)){
                return true;
            }
        }
        if(cl.getSuperclass()==Object.class||cl.getSuperclass()==null){
            return false;
        }else {
            return isImplemented(cl.getSuperclass(),face);
        }
    }
}
