package com.project.laboratory.controller.admin;



import com.baomidou.mybatisplus.core.metadata.IPage;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.laboratory.model.dto.LoginDTO;
import com.project.laboratory.model.dto.Result;
import com.project.laboratory.model.entity.Admin;
import com.project.laboratory.model.entity.InterviewResult;
import com.project.laboratory.model.entity.InterviewStudent;
import com.project.laboratory.model.entity.Student;
import com.project.laboratory.service.AdminService;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@ApiResponse(ref = "管理员相关接口")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    @ApiOperationSupport(author = "管理员登录")
    public Result<Admin> login(@Validated @RequestBody LoginDTO loginDTO) {
        Admin admin = adminService.login(loginDTO);
        return admin != null ? Result.success(admin) : Result.fail("账号或密码错误");
    }

    @GetMapping("/students/register")
    @ApiOperationSupport(author = "查看注册学生（分页+姓名搜索）")
    public Result<IPage<Student>> getRegisterStudents(@RequestParam int pageNum,
                                                      @RequestParam int pageSize,
                                                      @RequestParam(required = false) String name) {
        IPage<Student> page = adminService.getRegisterStudents(pageNum, pageSize, name);
        return Result.success(page);
    }

    @GetMapping("/students/apply")
    @ApiOperationSupport(author = "查看报名学生（分页+姓名/方向搜索）")
    public Result<IPage<InterviewStudent>> getApplyStudents(@RequestParam int pageNum,
                                                            @RequestParam int pageSize,
                                                            @RequestParam(required = false) String name,
                                                            @RequestParam(required = false) String direction) {
        IPage<InterviewStudent> page = adminService.getApplyStudents(pageNum, pageSize, name, direction);
        return Result.success(page);
    }

    @GetMapping("/interview/results")
    @ApiOperationSupport(author = "查看所有面试结果（分页）")
    public Result<IPage<InterviewResult>> getInterviewResults(@RequestParam int pageNum,
                                                              @RequestParam int pageSize) {
        IPage<InterviewResult> page = adminService.getInterviewResults(pageNum, pageSize);
        return Result.success(page);
    }

    @PostMapping("/student/apply")
    @ApiOperationSupport(author = "手动添加报名学生")
    public Result<?> addApplyStudent(@RequestBody InterviewStudent interviewStudent) {
        try {
            boolean success = adminService.addApplyStudent(interviewStudent);
            return success ? Result.success() : Result.fail("添加失败");
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @PutMapping("/student/apply")
    @ApiOperationSupport(author = "修改报名学生信息")
    public Result<?> updateApplyStudent(@RequestBody InterviewStudent interviewStudent) {
        try {
            boolean success = adminService.updateApplyStudent(interviewStudent);
            return success ? Result.success() : Result.fail("修改失败");
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @DeleteMapping("/student/apply/{id}")
    @ApiOperationSupport(author = "删除报名学生")
    public Result<?> deleteApplyStudent(@PathVariable Integer id) {
        try {
            boolean success = adminService.deleteApplyStudent(id);
            return success ? Result.success() : Result.fail("删除失败");
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/interview/result")
    @ApiOperationSupport(author = "录入/修改面试结果")
    public Result<?> saveInterviewResult(@RequestBody InterviewResult interviewResult) {
        try {
            boolean success = adminService.saveInterviewResult(interviewResult);
            return success ? Result.success() : Result.fail("操作失败");
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }
}
