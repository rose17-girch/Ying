package com.project.laboratory.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("interview_result")
public class InterviewResult {
    private Integer id;
    private String studentId;
    private String studentName;
    private String status;
    private Date createTime;
    private Date updateTime;
}
