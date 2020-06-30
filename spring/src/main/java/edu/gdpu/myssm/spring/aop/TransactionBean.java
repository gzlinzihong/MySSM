package edu.gdpu.myssm.spring.aop;

import java.sql.Connection;

/**
 * @author 嘿 林梓鸿
 */
public interface TransactionBean {

    Connection getConnection();
}
