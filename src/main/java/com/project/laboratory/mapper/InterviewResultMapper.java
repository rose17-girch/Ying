package com.project.laboratory.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.project.laboratory.model.entity.InterviewResult;
import org.apache.ibatis.annotations.Param;

public interface InterviewResultMapper extends BaseMapper<InterviewResult> {
    // 按学生学号查面试结果
    InterviewResult selectByStudentId(@Param("studentId") String studentId);
}

