package com.biubiu.config;

import lombok.Data;

/**
 * @author 张海彪
 * @create 2019-02-22 11:43
 */
@Data
public class Function {

    private String sqlType;

    private String funcName;

    private String sql;

    private Object resultType;

    private String parameterType;

}
