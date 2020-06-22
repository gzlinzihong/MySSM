package edu.gdpu.myssm.service;

import edu.gdpu.myssm.annotation.Service;
import edu.gdpu.myssm.annotation.UseAspect;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 00:33:51
 */
@UseAspect(value = "hello",exclude = "")
@Service
public class HelloServiceImpl implements HelloService{

    @Override
    public void test() {
            System.out.println("helloService test....");
    }
}
