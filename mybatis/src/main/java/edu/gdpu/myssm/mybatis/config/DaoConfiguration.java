package edu.gdpu.myssm.mybatis.config;

import edu.gdpu.myssm.mybatis.config.Environment;

import java.sql.Connection;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 17:26:08
 */
public class DaoConfiguration {

    private Environment environment;

    private MapperScanConfig mapperScanConfig;

    public DaoConfiguration() {
    }

    public DaoConfiguration(Environment environment) {
        this.environment = environment;
    }

    public Connection getConnection(){
        return environment.getConnection();
    }

    public MapperScanConfig getMapperScanConfig() {
        return mapperScanConfig;
    }

    public void setMapperScanConfig(MapperScanConfig mapperScanConfig) {
        this.mapperScanConfig = mapperScanConfig;
    }
}
