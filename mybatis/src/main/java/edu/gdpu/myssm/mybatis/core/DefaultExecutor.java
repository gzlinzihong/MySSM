package edu.gdpu.myssm.mybatis.core;

import edu.gdpu.myssm.mybatis.SqlType;
import edu.gdpu.myssm.mybatis.annotation.*;
import edu.gdpu.myssm.mybatis.core.Executor;
import edu.gdpu.myssm.mybatis.core.MethodMapper;
import edu.gdpu.myssm.spring.aop.Signature;
import edu.gdpu.myssm.utils.SqlExpressionParser;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 嘿 林梓鸿
 * @date 2020年 06月25日 14:26:14
 */
public class DefaultExecutor implements Executor {

    private Class mapperInterface;

    private Connection connection;

    private Map<Method, MethodMapper> methods = new HashMap<>();

    public DefaultExecutor(Class mapperInterface, Connection connection) {
        this.mapperInterface = mapperInterface;
        this.connection = connection;

        resolveClass();
    }

    private void resolveClass() {
        String sql = null;
        SqlType type = null;
        Signature signature = null;
        for(Method method:mapperInterface.getDeclaredMethods()){
            if(method.isAnnotationPresent(Insert.class)){
                Insert insert = method.getAnnotation(Insert.class);
                sql = insert.value();
                type = SqlType.INSERT;
            }else if(method.isAnnotationPresent(Update.class)){
                Update update = method.getAnnotation(Update.class);
                sql = update.value();
                type = SqlType.UPDATE;
            }else if(method.isAnnotationPresent(Select.class)){
                Select select = method.getAnnotation(Select.class);
                sql = select.value();
                type = SqlType.SELECT;
            }else if(method.isAnnotationPresent(Delete.class)){
                Delete delete = method.getAnnotation(Delete.class);
                sql = delete.value();
                type = SqlType.DELETE;
            }
            signature = new Signature(method.getName(),method.getGenericReturnType(),method.getParameterTypes());
            methods.put(method,new MethodMapper(sql,type,signature));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object execute(Method method,Object[] args) throws SQLException {
        MethodMapper methodMapper = methods.get(method);
        Class<?> returnType = method.getReturnType();
        String sql = methodMapper.getSql().replaceAll("#\\{[a-z0-9A-Z\\.]+}", "?");
        List<String> parse = SqlExpressionParser.parse(methodMapper.getSql());
        List<Object> sqlArgs = new ArrayList<>();
        for(String s:parse){
            try {
                Class<?> aClass = args[0].getClass();
                if(aClass == Integer.class|| aClass == int.class|| aClass == String.class){
                    sqlArgs.add(args[0]);
                    continue;
                }
                Field field = aClass.getDeclaredField(s);
                field.setAccessible(true);
                sqlArgs.add(field.get(args[0]));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        PreparedStatement pre = connection.prepareStatement(sql);
        for(int i = 0;i<sqlArgs.size();i++){
            pre.setObject(i+1,sqlArgs.get(i));
        }
        switch (methodMapper.getSqlType()){
            case DELETE:
                return pre.executeUpdate();
            case INSERT:
                return pre.executeUpdate();
            case UPDATE:
                return pre.executeUpdate();
            case SELECT:
                try {
                    if(returnType.isAssignableFrom(List.class)){
                        return selectList(methodMapper.getSignature(),pre);
                    }else {
                        return selectOne(returnType,pre);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return null;
    }


    public  <E> List<E> selectList(Signature signature,PreparedStatement pre) throws Exception {
        Type returnType = signature.getReturnType();
        Class<?> pojo = null;
        if(returnType instanceof ParameterizedType){
            ParameterizedType type = (ParameterizedType)returnType;
            Type[] types = type.getActualTypeArguments();
            pojo = (Class) types[0];
        }
        ResultSet resultSet = pre.executeQuery();
        List<E> list = new ArrayList<>();
        E o = null;
        while (resultSet.next()){
            assert pojo != null;
            o = (E) pojo.getDeclaredConstructor().newInstance();
            Field[] fields = pojo.getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                field.set(o,resultSet.getObject(field.getName()));
            }
            list.add(o);
        }
        return list;
    }

    public <T> T selectOne(Class c,PreparedStatement pre) throws Exception{
        ResultSet resultSet = pre.executeQuery();
        while (resultSet.next()){
            Object o = c.getDeclaredConstructor().newInstance();
            Field[] fields = o.getClass().getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                field.set(o,resultSet.getObject(field.getName()));
            }
            return (T) o;
        }
        return null;
    }
}
