package com.biubiu.config;

import lombok.Data;

import java.util.List;

/**
 * @author 张海彪
 * @create 2019-02-22 11:42
 */
@Data
public class MapperBean {

    private String interfaceName;//接口名

    private List<Function> list;//接口下所有方法

}
