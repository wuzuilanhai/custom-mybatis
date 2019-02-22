package com.biubiu.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 张海彪
 * @create 2019-02-19 12:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String id;

    private String username;

    private String password;

    private String salt;

    private String creator;

    private Date createTime;

    private String editor;

    private Date editTime;

    private Integer isDelete;

}
