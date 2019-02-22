package com.biubiu.sqlSession;

/**
 * @author 张海彪
 * @create 2019-02-22 12:38
 */
public interface Executor {

    <T> T query(String sql, Object parameter);

}
