package edu.gdpu.myssm.mybatis.core;

import edu.gdpu.myssm.mybatis.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 14:22:59
 */
public class MapperProxy<T> implements InvocationHandler {

    private Class<T> mapperInterface;

    private SqlSession sqlSession;

    private Executor executor;

    public MapperProxy(Class mapperInterface,SqlSession sqlSession) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
        executor = new DefaultExecutor(mapperInterface,sqlSession.getConnection());
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        String name = method.getName();
        if(name.equals("toString")||name.equals("equals")||name.equals("hashCode")){
            return method.invoke(this, objects);
        }

        return executor.execute(method,objects);
    }
}
