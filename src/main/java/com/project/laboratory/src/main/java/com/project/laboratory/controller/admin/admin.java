package com.project.laboratory.controller.admin;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("admin")
public class admin {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String createTime;
    private String updateTime;
}
