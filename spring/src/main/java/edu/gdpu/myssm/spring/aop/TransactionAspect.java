package edu.gdpu.myssm.spring.aop;

import edu.gdpu.myssm.spring.ApplicationContext;
import edu.gdpu.myssm.spring.annotation.Aspect;
import edu.gdpu.myssm.spring.annotation.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月30日 13:38:25
 */
@Aspect("transaction")
public class TransactionAspect implements Advice {

    private Connection connection;
    @Override
    public void before(JoinPoint joinPoint) {
        TransactionBean tb = ApplicationContext.getApplicationContext().getBean("transactionBean");
        connection = tb.getConnection();
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void after(JoinPoint joinPoint) {
        try {
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterThrowing(JoinPoint joinPoint, Throwable e) {
        e.printStackTrace();
        try {
            connection.rollback();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Object afterReturning(JoinPoint joinPoint, Object o) {
        return o;
    }
}
