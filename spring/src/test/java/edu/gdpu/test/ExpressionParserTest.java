package edu.gdpu.test;

import edu.gdpu.myssm.spring.exception.ParseExpressionException;
import edu.gdpu.myssm.utils.ExpressionParser;
import org.junit.Test;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月18日 17:43:55
 */
public class ExpressionParserTest {

    @Test
    public void testParse() throws ParseExpressionException {
        String parse = ExpressionParser.parse("${jdbc.user}");
        System.out.println(parse);
    }
}
