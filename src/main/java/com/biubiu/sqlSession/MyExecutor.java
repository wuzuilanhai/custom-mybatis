package com.biubiu.sqlSession;

import com.biubiu.po.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author 张海彪
 * @create 2019-02-22 12:40
 */
public class MyExecutor implements Executor {

    private MyConfiguration xmlConfiguration = new MyConfiguration();

    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, parameter.toString());
            resultSet = statement.executeQuery();
            User user = new User();
            //遍历结果集
            while (resultSet.next()) {
                user.setId(resultSet.getString(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setSalt(resultSet.getString(4));
                user.setCreator(resultSet.getString(5));
                user.setCreateTime(resultSet.getTimestamp(6));
                user.setEditor(resultSet.getString(7));
                user.setEditTime(resultSet.getTimestamp(8));
                user.setIsDelete(resultSet.getInt(9));
            }
            return (T) user;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private Connection getConnection() {
        return xmlConfiguration.build("config.xml");
    }
}
