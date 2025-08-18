package com.project.laboratory.mapper;




import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.project.laboratory.model.entity.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentMapper extends BaseMapper<Student> {
    // 分页+姓名模糊查询学生
    IPage<Student> selectStudentPage(Page<Student> page, @Param("name") String name);
}