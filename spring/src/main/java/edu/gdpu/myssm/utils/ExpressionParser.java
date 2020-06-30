package edu.gdpu.myssm.utils;

import edu.gdpu.myssm.spring.exception.ParseExpressionException;


/**
 * 表达式解析器
 *
 * @author 嘿 林梓鸿
 * @date 2020年 06月18日 17:37:54
 */
public class ExpressionParser {

    public static String parse(String expression) throws ParseExpressionException {

        if(!expression.startsWith("${")){
            throw new ParseExpressionException("格式错误");
        }
        if(!expression.endsWith("}")){
            throw new ParseExpressionException("格式错误");
        }
        String[] split = expression.split("\\{");

        if(split.length!=2){
            throw new ParseExpressionException("格式错误");
        }else {
            String[] s = split[1].split("}");
            if(s.length!=1){
                throw new ParseExpressionException("格式错误");
            }
            else {
                return s[0];
            }
        }

    }

}

