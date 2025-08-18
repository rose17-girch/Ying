package com.project.laboratory.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("student")
public class Student {
    private Integer id;
    private String password;
    private String name;
    private String major;
    private String studentId;
    private String phone;
    private String email;
    private Date createTime;
    private Date updateTime;
}