package edu.gdpu.myssm.aop;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 14:51:05
 */
public class Signature {

    private String name;

    private Class returnType;

    private Class[] parameterTypes;

    public Signature(String name, Class returnType, Class[] parameterTypes) {
        this.name = name;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

    public String getName() {
        return name;
    }

    public Class getReturnType() {
        return returnType;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }
}
