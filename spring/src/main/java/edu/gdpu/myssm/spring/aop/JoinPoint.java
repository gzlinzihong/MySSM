package edu.gdpu.myssm.spring.aop;

import java.util.Arrays;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月22日 14:37:15
 */
public class JoinPoint {

    private Object target;

    private Signature signature;

    private Object[] args;

    private Object proxy;

    public JoinPoint(Object target, Signature signature, Object[] args,Object proxy) {
        this.target = target;
        this.signature = signature;
        this.args = args;
        this.proxy = proxy;
    }

    public Object getTarget() {
        return target;
    }

    public Signature getSignature() {
        return signature;
    }


    public Object[] getObjects() {
        return args;
    }

    public Object getThis(){
        return proxy;
    }

    @Override
    public String toString() {
        return "JoinPoint{" +
                "target=" + target +
                ", signature=" + signature +
                ", args=" + Arrays.toString(args) +
                ", proxy=" + proxy +
                '}';
    }
}
