package com.project.laboratory.controller.user;






import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;

import com.project.laboratory.model.dto.LoginDTO;
import com.project.laboratory.model.dto.Result;
import com.project.laboratory.model.entity.InterviewResult;
import com.project.laboratory.model.entity.InterviewStudent;
import com.project.laboratory.model.entity.Student;
import com.project.laboratory.service.StudentService;


import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/student")
@ApiResponse(ref = "学生相关接口")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/register")
    @ApiOperationSupport(author = "学生注册")
    public Result<?> register(@RequestBody Student student) {
        try {
            boolean success = studentService.register(student);
            return success ? Result.success() : Result.fail("注册失败");
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/login")
    @ApiOperationSupport(author = "学生登录")
    public Result<Student> login(@Validated @RequestBody LoginDTO loginDTO) {
        Student student = studentService.login(loginDTO);
        return student != null ? Result.success(student) : Result.fail("账号或密码错误");
    }

    @PostMapping("/apply")
    @ApiOperationSupport(author = "学生报名面试")
    public Result<?> applyInterview(@RequestBody InterviewStudent interviewStudent) {
        try {
            boolean success = studentService.applyInterview(interviewStudent);
            return success ? Result.success() : Result.fail("报名失败");
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/interview/result")
    @ApiOperationSupport(author = "查看个人面试结果")
    public Result<InterviewResult> getMyInterviewResult(@RequestParam String studentId) {
        InterviewResult result = studentService.getMyInterviewResult(studentId);
        return Result.success(result);
    }

    @GetMapping("/direction/data")
    @ApiOperationSupport(author = "查看四大方向数据")
    public Result<Map<String, Object>> getDirectionData() {
        return Result.success(studentService.getDirectionData());
    }

    @GetMapping("/direction/path")
    @ApiOperationSupport(author = "查看四大方向学习路线")
    public Result<Map<String, Object>> getDirectionStudyPath() {
        return Result.success(studentService.getDirectionStudyPath());
    }

    @PutMapping("/password")
    @ApiOperationSupport(author = "修改密码")
    public Result<?> updatePassword(@RequestParam String studentId,
                                    @RequestParam String oldPwd,
                                    @RequestParam String newPwd) {
        try {
            boolean success = studentService.updatePassword(studentId, oldPwd, newPwd);
            return success ? Result.success() : Result.fail("修改失败");
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }
}