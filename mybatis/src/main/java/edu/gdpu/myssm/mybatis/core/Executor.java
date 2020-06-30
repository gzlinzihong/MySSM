package edu.gdpu.myssm.mybatis.core;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 14:30:39
 */
public interface Executor {

    Object execute(Method method,Object[] args) throws SQLException;

}
