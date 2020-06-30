package edu.gdpu.myssm.mybatis.config;

import java.util.List;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 13:25:04
 */
public class MapperScanConfig {

    private String packageName;

    private List<String> exclude;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(List<String> exclude) {
        this.exclude = exclude;
    }
}
