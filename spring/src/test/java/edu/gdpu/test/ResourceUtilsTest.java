package edu.gdpu.test;

import edu.gdpu.myssm.utils.PropertiesUtils;
import edu.gdpu.myssm.utils.ResourceUtils;
import org.junit.Test;

import java.util.Properties;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月18日 17:27:31
 */
public class ResourceUtilsTest {

    @Test
    public void testLoad(){
        Properties test = PropertiesUtils.load("test");
        String user = test.getProperty("user");
        System.out.println(user);
    }
}
