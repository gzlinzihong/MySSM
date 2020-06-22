package edu.gdpu.myssm.handler;

import edu.gdpu.myssm.AnnotationHandler;
import edu.gdpu.myssm.annotation.Value;
import edu.gdpu.myssm.exception.ParseExpressionException;
import edu.gdpu.myssm.utils.ExpressionParser;

import java.lang.reflect.Field;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月21日 12:46:35
 */
public class ValueHandler implements AnnotationHandler {
    @Override
    public Object handle(Object o) {
        Field field = (Field)o;
        field.setAccessible(true);
        if(!field.isAnnotationPresent(Value.class)){
            return null;
        }
        Value annotation = field.getAnnotation(Value.class);
        try {
            return ExpressionParser.parse(annotation.value());
        } catch (ParseExpressionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
