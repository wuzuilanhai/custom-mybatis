package com.biubiu.sqlSession;

import com.biubiu.config.Function;
import com.biubiu.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 读取与解析配置信息，并返回处理后的Environment
 *
 * @author 张海彪
 * @create 2019-02-22 11:02
 */
public class MyConfiguration {

    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    /**
     * 读取xml文件并处理
     */
    public Connection build(String resource) {
        InputStream inputStream = loader.getResourceAsStream(resource);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (Exception e) {
            throw new RuntimeException("error occured while evaling xml " + resource);
        }
    }

    public MapperBean readMapper(String path) {
        MapperBean mapper = new MapperBean();
        InputStream inputStream = loader.getResourceAsStream(path);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            //把mapper节点的nameSpace值存为接口名
            mapper.setInterfaceName(root.attributeValue("nameSpace").trim());
            List<Function> list = new ArrayList<>();
            for (Iterator iterator = root.elementIterator(); iterator.hasNext(); ) {
                Function func = new Function();
                Element e = (Element) iterator.next();
                String sqlType = e.getName().trim();
                String funcName = e.attributeValue("id").trim();
                String sql = e.getText().trim();
                String resultType = e.attributeValue("resultType").trim();

                Object instance = Class.forName(resultType).newInstance();

                func.setResultType(instance);
                func.setSqlType(sqlType);
                func.setFuncName(funcName);
                func.setSql(sql);
                list.add(func);
            }
            mapper.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapper;
    }

    private Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!node.getName().equals("database")) {
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        //获取属性节点
        for (Object item : node.elements("property")) {
            Element ele = (Element) item;
            String value = getValue(ele);
            String name = ele.attributeValue("name");
            if (name == null) {
                throw new RuntimeException("[database]: <property>should contain name and value");
            }
            switch (name) {
                case "driverClassName":
                    driverClassName = value;
                    break;
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                default:
                    throw new RuntimeException("[database]: <property> unknown name");
            }
        }
        return getConnection(driverClassName, url, username, password);
    }

    private Connection getConnection(String driverClassName, String url, String username, String password) throws ClassNotFoundException {
        Class.forName(driverClassName);
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
    private String getValue(Element ele) {
        return ele.hasContent() ? ele.getText() : ele.attributeValue("value");
    }

}
