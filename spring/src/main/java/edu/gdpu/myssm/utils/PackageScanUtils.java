package edu.gdpu.myssm.utils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月19日 17:50:55
 */
public class PackageScanUtils {

    private static ClassLoader loader = Thread.currentThread().getContextClassLoader();

    public static Set<Class> scanPackage(String packageName){
        Set<Class> classes = new HashSet<>();
        try {
            String s1 = packageName.replaceAll("\\.", "/");
            Enumeration<URL> resources = loader.getResources(s1);
            while (resources.hasMoreElements()){
                URL url = resources.nextElement();
                String file = url.getFile();
                if(url.getProtocol().equals("jar")){
                    classes.addAll(parseJar(url,packageName));
                    continue;
                }
                List<File> allFiles = FileUtils.getAllFiles(new File(file));
                String[] split = packageName.split("\\.");
                List<String> name = FileUtils.getPackageName(allFiles, split[split.length - 1]);
                String className = null;
                for(String s:name){
                    className = (packageName+"."+s).split("\\.class")[0];
                    Class<?> aClass = loader.loadClass(className);
                    classes.add(aClass);
                }
            }
            return classes;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public static Set<Class> parseJar(URL url,String packageName) throws IOException {

        Set<Class> classes = new HashSet<>();
        Enumeration<JarEntry> jarEntries = ((JarURLConnection) url.openConnection()).getJarFile().entries();

        while (jarEntries.hasMoreElements()){
            JarEntry jarEntry = jarEntries.nextElement();
            String name = jarEntry.getName();
            if(name.endsWith(".class")){
                String s = name.replaceAll("/", ".");
                try {
                    String className = s.split("\\.class")[0];
                    if(className.startsWith(packageName)){
                        Class<?> aClass = loader.loadClass(className);
                        classes.add(aClass);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

}

