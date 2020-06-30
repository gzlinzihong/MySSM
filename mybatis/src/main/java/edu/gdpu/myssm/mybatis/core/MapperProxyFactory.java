package edu.gdpu.myssm.mybatis.core;

import edu.gdpu.myssm.mybatis.SqlSession;

import java.lang.reflect.Proxy;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 14:19:04
 */
public class MapperProxyFactory<T> {

    private Class<T> mapperInterface;

    private SqlSession sqlSession;

    public MapperProxyFactory(Class<T> mapperInterface,SqlSession sqlSession) {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
    }

    @SuppressWarnings("unchecked")
    public <T> T newInstance(){
        MapperProxy<T> tMapperProxy = new MapperProxy<T>(mapperInterface, sqlSession);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(),new Class[]{mapperInterface},tMapperProxy);
    }
}
