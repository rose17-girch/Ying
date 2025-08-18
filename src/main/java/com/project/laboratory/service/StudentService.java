package com.project.laboratory.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.project.laboratory.model.dto.LoginDTO;
import com.project.laboratory.model.entity.InterviewResult;
import com.project.laboratory.model.entity.InterviewStudent;
import com.project.laboratory.model.entity.Student;

import java.util.Map;

public interface StudentService {
    // 注册
    boolean register(Student student);
    // 登录
    Student login(LoginDTO loginDTO);
    // 分页查学生（管理员用）
    IPage<Student> getStudentPage(int pageNum, int pageSize, String name);
    // 学生报名
    boolean applyInterview(InterviewStudent interviewStudent);
    // 查个人面试结果
    InterviewResult getMyInterviewResult(String studentId);
    // 查四大方向数据
    Map<String, Object> getDirectionData();
    // 查四大方向学习路线
    Map<String, Object> getDirectionStudyPath();
    // 修改密码
    boolean updatePassword(String studentId, String oldPwd, String newPwd);
}
