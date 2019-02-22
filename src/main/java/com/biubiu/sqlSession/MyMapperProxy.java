package com.biubiu.sqlSession;

import com.biubiu.config.Function;
import com.biubiu.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author 张海彪
 * @create 2019-02-22 12:53
 */
public class MyMapperProxy implements InvocationHandler {

    private MySqlSession mySqlSession;

    private MyConfiguration myConfiguration;

    public MyMapperProxy(MySqlSession mySqlSession, MyConfiguration myConfiguration) {
        this.mySqlSession = mySqlSession;
        this.myConfiguration = myConfiguration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean = myConfiguration.readMapper("UserMapper.xml");
        //是否是xml文件对应的接口
        if (!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())) {
            return null;
        }
        List<Function> list = mapperBean.getList();
        if (list != null && !list.isEmpty()) {
            for (Function function : list) {
                //id是否和接口方法名一样
                if (method.getName().equals(function.getFuncName())) {
                    return mySqlSession.selectOne(function.getSql(), String.valueOf(args[0]));
                }
            }
        }
        return null;
    }

}
