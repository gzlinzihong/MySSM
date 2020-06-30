package edu.gdpu.myssm.mybatis;

import edu.gdpu.myssm.mybatis.config.DaoConfiguration;
import edu.gdpu.myssm.mybatis.core.MapperProxyFactory;
import edu.gdpu.myssm.spring.aop.TransactionBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 14:09:27
 */
public class DefaultSqlSession implements SqlSession, TransactionBean {

    private DaoConfiguration configuration;

    private Connection connection;


    public DefaultSqlSession(DaoConfiguration configuration){
        this.configuration = configuration;
        this.connection = configuration.getConnection();
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        return new MapperProxyFactory<T>(type,this).newInstance();
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public DaoConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void close() throws IOException {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
