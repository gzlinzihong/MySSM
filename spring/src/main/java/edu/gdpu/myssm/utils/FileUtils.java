package edu.gdpu.myssm.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月18日 23:33:57
 */
public class FileUtils {

    public static List<File> getAllFiles(File file) throws FileNotFoundException {
        List<File> files = new ArrayList<>();
        ArrayList<File> dirs = new ArrayList<>();
        if (!file.exists()){
            throw new FileNotFoundException("文件不存在");
        }
        else {
            //第一次获取目录
            dirs.addAll(Arrays.asList(file.listFiles()));

            //新建一个集合来存储遍历是时候是目录的File对象
            ArrayList<File> checks = new ArrayList<>();
            while (dirs.size()!=0){
                for (File file1:dirs){
                    if (!file1.isDirectory()){
                        files.add(file1);
                    }
                    else {
                        checks.addAll(Arrays.asList(file1.listFiles()));
                    }
                }
                dirs.clear();
                dirs.addAll(checks);
                checks.clear();
            }
        }
        return files;
    }


    public static List<String> getPackageName(List<File> files,String parentPackage){
        List<String> name = new ArrayList<>();
        for(File file:files){
            File temp = new File(file.getAbsolutePath());
            if(temp.getName().equals(parentPackage)){
                name.add("");
                continue;
            }
            String s = "";
            while (!temp.getName().equals(parentPackage)){
                s = temp.getName()+"."+s;
                temp = temp.getParentFile();
            }
            name.add(s);
        }
        return name;
    }
}

