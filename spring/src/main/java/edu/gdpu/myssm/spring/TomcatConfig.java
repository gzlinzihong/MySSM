package edu.gdpu.myssm.spring;

import edu.gdpu.myssm.spring.annotation.Configuration;
import edu.gdpu.myssm.spring.annotation.Resource;
import edu.gdpu.myssm.spring.annotation.Value;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 07月01日 10:28:28
 */
@Configuration
@Resource
public class TomcatConfig {

    @Value("${tomcat.port}")
    private Integer port = 8080;

    @Value("${tomcat.contextPath}")
    private String contextPath = "/";

    private String baseDir = "src/main/webapp";

    public Integer getPort() {
        return port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getBaseDir() {
        return baseDir;
    }
}
