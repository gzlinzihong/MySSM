package edu.gdpu;

import edu.gdpu.myssm.Application;
import edu.gdpu.myssm.ApplicationContext;
import edu.gdpu.myssm.controller.HelloController;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 00:31:18
 */
public class TestApplication {

    public static void main(String[] args) {
        Application.run(TestApplication.class);
        HelloController hellocontroller = (HelloController) ApplicationContext.getApplicationContext().getBeans().get("hellocontroller");
        hellocontroller.ha();
    }
}
