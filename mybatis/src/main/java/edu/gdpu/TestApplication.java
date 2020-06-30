package edu.gdpu;

import edu.gdpu.myssm.mybatis.test.AccountService;
import edu.gdpu.myssm.mybatis.test.AccountServiceImpl;
import edu.gdpu.myssm.spring.Application;
import edu.gdpu.myssm.spring.ApplicationContext;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 00:31:18
 */
public class TestApplication {

    public static void main(String[] args) throws Exception {
        Application.run(TestApplication.class);
        AccountService accountServiceImpl = ApplicationContext.getApplicationContext().getBean(AccountServiceImpl.class);
        accountServiceImpl.test();
        accountServiceImpl.test1();
    }
}
