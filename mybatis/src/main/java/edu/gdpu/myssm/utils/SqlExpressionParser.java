package edu.gdpu.myssm.utils;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 15:06:04
 */
public class SqlExpressionParser {

    public static List<String> parse(String sql){
        List<String> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        do {
            i = sql.indexOf("}",i+1);
            if(i==-1){
                break;
            }
            list.add(sql.substring(sql.indexOf("#{",j)+2, i));
            j = i;
        }while (true);
        return list;
    }


}
