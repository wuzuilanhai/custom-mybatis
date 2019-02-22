package com.biubiu.sqlSession;

import java.lang.reflect.Proxy;

/**
 * @author 张海彪
 * @create 2019-02-22 12:38
 */
public class MySqlSession {

    private Executor executor = new MyExecutor();

    private MyConfiguration myConfiguration = new MyConfiguration();

    public <T> T selectOne(String sql, Object parameter) {
        return executor.query(sql, parameter);
    }

    public <T> T getMapper(Class<T> clazz) {
        //动态代理调用
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new MyMapperProxy(this, myConfiguration));
    }

}
