package com.project.laboratory.model.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("admin")
public class Admin {
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private Date createTime;
    private Date updateTime;
}
