package com.project.laboratory.controller.Interview;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("interview_student")
public class Student {
    private Integer id;
    private String name;
    private String email;
    private String intentDirection;
    private String phone;
    private String motto;
    private Date interviewTime;
    private String studentId;
    private Date createTime;
    private Date updateTime;
}