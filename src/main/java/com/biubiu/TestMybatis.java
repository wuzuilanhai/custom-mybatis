package com.biubiu;

import com.biubiu.mapper.UserMapper;
import com.biubiu.po.User;
import com.biubiu.sqlSession.MySqlSession;

/**
 * @author 张海彪
 * @create 2019-02-22 10:55
 */
public class TestMybatis {

    public static void main(String[] args) {
        MySqlSession mySqlSession = new MySqlSession();
        UserMapper userMapper = mySqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUserById("218227154331107328");
        System.out.println(user);
    }

}
