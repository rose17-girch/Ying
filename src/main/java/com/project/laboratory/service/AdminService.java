package com.project.laboratory.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.laboratory.model.dto.LoginDTO;
import com.project.laboratory.model.entity.Admin;
import com.project.laboratory.model.entity.InterviewResult;
import com.project.laboratory.model.entity.InterviewStudent;
import com.project.laboratory.model.entity.Student;

public interface AdminService {
    // 登录
    Admin login(LoginDTO loginDTO);
    // 查注册学生（分页+搜索）
    IPage<Student> getRegisterStudents(int pageNum, int pageSize, String name);
    // 查报名学生（分页+搜索）
    IPage<InterviewStudent> getApplyStudents(int pageNum, int pageSize, String name, String direction);
    // 查所有面试结果
    IPage<InterviewResult> getInterviewResults(int pageNum, int pageSize);
    // 新增报名学生
    boolean addApplyStudent(InterviewStudent interviewStudent);
    // 修改报名学生
    boolean updateApplyStudent(InterviewStudent interviewStudent);
    // 删除报名学生
    boolean deleteApplyStudent(Integer id);
    // 录入/修改面试结果
    boolean saveInterviewResult(InterviewResult interviewResult);
}
