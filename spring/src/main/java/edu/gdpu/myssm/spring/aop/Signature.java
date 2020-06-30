package edu.gdpu.myssm.spring.aop;

import java.lang.reflect.Type;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 14:51:05
 */
public class Signature {

    private String name;

    private Type returnType;

    private Class[] parameterTypes;

    public Signature(String name, Type returnType, Class[] parameterTypes) {
        this.name = name;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

    public String getName() {
        return name;
    }

    public Type getReturnType() {
        return returnType;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }
}
