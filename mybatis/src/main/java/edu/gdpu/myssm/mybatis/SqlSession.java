package edu.gdpu.myssm.mybatis;

import edu.gdpu.myssm.mybatis.config.DaoConfiguration;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;

/**
 * @author 嘿 林梓鸿
 */
public interface SqlSession extends AutoCloseable {


    <T> T getMapper(Class<T> type);

    Connection getConnection();

    DaoConfiguration getConfiguration();

    @Override
    void close() throws IOException;
}
