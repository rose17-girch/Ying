package com.project.laboratory.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.laboratory.mapper.AdminMapper;
import com.project.laboratory.mapper.InterviewResultMapper;
import com.project.laboratory.mapper.InterviewStudentMapper;
import com.project.laboratory.mapper.StudentMapper;
import com.project.laboratory.model.dto.LoginDTO;
import com.project.laboratory.model.entity.Admin;
import com.project.laboratory.model.entity.InterviewResult;
import com.project.laboratory.model.entity.InterviewStudent;
import com.project.laboratory.model.entity.Student;
import com.project.laboratory.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private InterviewStudentMapper interviewStudentMapper;
    @Autowired
    private InterviewResultMapper interviewResultMapper;

    @Override
    public Admin login(LoginDTO loginDTO) {
        Admin admin = adminMapper.selectByUsername(loginDTO.getAccount());
        if (admin == null || !admin.getPassword().equals(loginDTO.getPassword())) {
            return null;
        }
        return admin;
    }

    @Override
    public IPage<Student> getRegisterStudents(int pageNum, int pageSize, String name) {
        return studentMapper.selectStudentPage(new Page<>(pageNum, pageSize), name);
    }

    @Override
    public IPage<InterviewStudent> getApplyStudents(int pageNum, int pageSize, String name, String direction) {
        return interviewStudentMapper.selectApplyPage(new Page<>(pageNum, pageSize), name, direction);
    }

    @Override
    public IPage<InterviewResult> getInterviewResults(int pageNum, int pageSize) {
        return interviewResultMapper.selectPage(new Page<>(pageNum, pageSize), null);
    }

    @Override
    public boolean addApplyStudent(InterviewStudent interviewStudent) {

        // 学生注册校验
        if (studentMapper.selectCount(new QueryWrapper<Student>().eq("student_id", interviewStudent.getStudentId())) == 0) {
            throw new RuntimeException("学生未注册");
        }
        // 重复报名校验
        if (interviewStudentMapper.selectCount(new QueryWrapper<InterviewStudent>().eq("student_id", interviewStudent.getStudentId())) > 0) {
            throw new RuntimeException("已报名");
        }
        return interviewStudentMapper.insert(interviewStudent) > 0;
    }

    @Override
    public boolean updateApplyStudent(InterviewStudent interviewStudent) {
        return false;
    }


    @Override
    public boolean deleteApplyStudent(Integer id) {
        // 查报名信息
        InterviewStudent student = interviewStudentMapper.selectById(id);
        if (student == null) {
            throw new RuntimeException("报名记录不存在");
        }
        // 删除报名记录
        interviewStudentMapper.deleteById(id);
        // 级联删除面试结果
        interviewResultMapper.delete(new QueryWrapper<InterviewResult>().eq("student_id", student.getStudentId()));
        return true;
    }

    @Override
    public boolean saveInterviewResult(InterviewResult interviewResult) {
        // 状态校验
        if (!"待面试".equals(interviewResult.getStatus())
                && !"通过".equals(interviewResult.getStatus())
                && !"未通过".equals(interviewResult.getStatus())) {
            throw new RuntimeException("状态仅支持：待面试/通过/未通过");
        }
        // 学生存在校验
        if (studentMapper.selectCount(new QueryWrapper<Student>().eq("student_id", interviewResult.getStudentId())) == 0) {
            throw new RuntimeException("学生不存在");
        }        // 检查是否已存在该学生的面试结果（存在则更新，不存在则新增）
        InterviewResult existResult = interviewResultMapper.selectByStudentId(interviewResult.getStudentId());
        if (existResult != null) {
            existResult.setStatus(interviewResult.getStatus());
            existResult.setStudentName(interviewResult.getStudentName());
            return interviewResultMapper.updateById(existResult) > 0;
        } else {
            return interviewResultMapper.insert(interviewResult) > 0;
        }
    }
}


