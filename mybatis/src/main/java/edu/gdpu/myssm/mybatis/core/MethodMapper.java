package edu.gdpu.myssm.mybatis.core;

import edu.gdpu.myssm.mybatis.SqlType;
import edu.gdpu.myssm.spring.aop.Signature;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 14:36:44
 */
public class MethodMapper {

    private String sql;

    private SqlType sqlType;

    private Signature signature;

    public MethodMapper(String sql, SqlType sqlType, Signature signature) {
        this.sql = sql;
        this.sqlType = sqlType;
        this.signature = signature;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public Signature getSignature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }
}
