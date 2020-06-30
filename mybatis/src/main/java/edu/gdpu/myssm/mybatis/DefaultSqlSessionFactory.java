package edu.gdpu.myssm.mybatis;

import edu.gdpu.myssm.mybatis.config.DaoConfiguration;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 14:01:14
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private DaoConfiguration configuration;

    public DefaultSqlSessionFactory(DaoConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }

}
