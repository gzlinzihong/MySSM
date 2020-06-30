package edu.gdpu.myssm.mybatis.config;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 17:26:31
 */
public final class Environment {

    private DataSource dataSource;

    public Environment(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
