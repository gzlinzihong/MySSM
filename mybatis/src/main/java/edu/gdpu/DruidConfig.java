package edu.gdpu;

import com.alibaba.druid.pool.DruidDataSource;
import edu.gdpu.myssm.mybatis.config.DaoConfiguration;
import edu.gdpu.myssm.mybatis.config.Environment;
import edu.gdpu.myssm.mybatis.config.MapperScanConfig;
import edu.gdpu.myssm.spring.annotation.Bean;
import edu.gdpu.myssm.spring.annotation.Configuration;
import edu.gdpu.myssm.spring.annotation.Resource;
import edu.gdpu.myssm.spring.annotation.Value;

import javax.sql.DataSource;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月23日 15:52:52
 */
@Configuration
@Resource
public class DruidConfig {

    @Value("${mysql.url}")
    private String url;

    @Value("${mysql.user}")
    private String user;

    @Value("${mysql.password}")
    private String password;

    /**
     * 要扫描的包
     * @return
     */
    @Bean
    public MapperScanConfig mapperScanConfig(){
        MapperScanConfig mapperScanConfig = new MapperScanConfig();
        mapperScanConfig.setPackageName("edu.gdpu.myssm.mybatis.test");
        return mapperScanConfig;
    }

    /**
     * 必须注入
     * @return
     */
    @Bean
    public DaoConfiguration daoConfiguration(){
        DaoConfiguration daoConfiguration = new DaoConfiguration(new Environment(druidDataSource()));
        daoConfiguration.setMapperScanConfig(mapperScanConfig());
        return daoConfiguration;
    }

    public DataSource druidDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

}
