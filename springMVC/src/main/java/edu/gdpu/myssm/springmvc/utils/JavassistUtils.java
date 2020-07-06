package edu.gdpu.myssm.springmvc.utils;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月05日 22:01:20
 */
public class JavassistUtils {

    public static String[] getParameterNames(Class cl, Method method){
        int length = method.getParameterTypes().length;
        String[] names = new String[length];
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ctClass = pool.get(cl.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName());
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                int len = ctMethod.getParameterTypes().length;
                // 非静态的成员函数的第一个参数是this
                int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
                for (int i = 0; i < len; i++) {
                    names[i] = attr.variableName(i + pos);
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return names;
    }
}
