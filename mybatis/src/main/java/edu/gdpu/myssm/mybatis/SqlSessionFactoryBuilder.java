package edu.gdpu.myssm.mybatis;

import edu.gdpu.myssm.mybatis.config.DaoConfiguration;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 14:16:42
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(DaoConfiguration configuration){
        return new DefaultSqlSessionFactory(configuration);
    }
}
