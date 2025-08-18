package com.project.laboratory.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.project.laboratory.model.entity.InterviewStudent;
import org.apache.ibatis.annotations.Param;

public interface InterviewStudentMapper extends BaseMapper<InterviewStudent> {
    // 分页+姓名/方向查询报名学生
    IPage<InterviewStudent> selectApplyPage(Page<InterviewStudent> page,
                                            @Param("name") String name,
                                            @Param("direction") String direction);
    // 按学生学号查报名信息
    InterviewStudent selectByStudentId(@Param("studentId") String studentId);
}
