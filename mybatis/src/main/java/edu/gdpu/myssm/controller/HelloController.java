package edu.gdpu.myssm.controller;

import edu.gdpu.myssm.annotation.AutoWired;
import edu.gdpu.myssm.annotation.Controller;
import edu.gdpu.myssm.service.HelloService;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 00:31:55
 */

@Controller
public class HelloController {

    @AutoWired
    private HelloService helloService;

    public void  ha(){
        helloService.test();
    }
}
