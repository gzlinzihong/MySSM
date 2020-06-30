package edu.gdpu.myssm.mybatis.test;

import edu.gdpu.myssm.spring.annotation.AutoWired;
import edu.gdpu.myssm.spring.annotation.Service;
import edu.gdpu.myssm.spring.annotation.Transaction;

import java.util.List;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月26日 12:25:32
 */
@Service
@Transaction(exclude = {"test1"})
public class AccountServiceImpl implements AccountService {

    @AutoWired
    private AccountDao accountDao;

    @Override
    public void test() {
        Account account = new Account();
        account.setMoney(1500);
        account.setName("张三");
        Account account1 = new Account();
        account1.setMoney(1500);
        account1.setName("李四");

        accountDao.update(account);
        int a = 10/0;
        accountDao.update(account1);

    }

    @Override
    public void test1() {
        Account account = new Account();
        account.setMoney(1000);
        account.setName("张三");
        Account account1 = new Account();
        account1.setMoney(2000);
        account1.setName("李四");

        accountDao.update(account);
        int a = 10/0;
        accountDao.update(account1);

    }
}
