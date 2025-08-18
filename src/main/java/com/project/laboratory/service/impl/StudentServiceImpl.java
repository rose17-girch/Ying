package com.project.laboratory.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.laboratory.mapper.InterviewResultMapper;
import com.project.laboratory.mapper.InterviewStudentMapper;
import com.project.laboratory.mapper.StudentMapper;
import com.project.laboratory.model.dto.LoginDTO;
import com.project.laboratory.model.entity.InterviewResult;
import com.project.laboratory.model.entity.InterviewStudent;
import com.project.laboratory.model.entity.Student;
import com.project.laboratory.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private InterviewStudentMapper interviewStudentMapper;
    @Autowired
    private InterviewResultMapper interviewResultMapper;

    @Override
    public boolean register(Student student) {

        // 密码校验
        if (student.getPassword().length() <= 6) {
            throw new RuntimeException("密码必须大于6位");
        }
        // 学号唯一性校验
        if (studentMapper.selectCount(new QueryWrapper<Student>().eq("student_id", student.getStudentId())) > 0) {
            throw new RuntimeException("学号已注册");
        }
        return studentMapper.insert(student) > 0;
    }

    @Override
    public Student login(LoginDTO loginDTO) {
        return studentMapper.selectOne(new QueryWrapper<Student>()
                .eq("student_id", loginDTO.getAccount())
                .eq("password", loginDTO.getPassword()));
    }

    @Override
    public IPage<Student> getStudentPage(int pageNum, int pageSize, String name) {
        return studentMapper.selectStudentPage(new Page<>(pageNum, pageSize), name);
    }

    @Override
    public boolean applyInterview(InterviewStudent interviewStudent) {

        // 学生是否注册校验
        if (studentMapper.selectCount(new QueryWrapper<Student>().eq("student_id", interviewStudent.getStudentId())) == 0) {
            throw new RuntimeException("未注册，无法报名");
        }
        // 重复报名校验
        if (interviewStudentMapper.selectCount(new QueryWrapper<InterviewStudent>().eq("student_id", interviewStudent.getStudentId())) > 0) {
            throw new RuntimeException("已报名，无需重复提交");
        }
        return interviewStudentMapper.insert(interviewStudent) > 0;
    }

    @Override
    public InterviewResult getMyInterviewResult(String studentId) {
        return interviewResultMapper.selectByStudentId(studentId);
    }

    @Override
    public Map<String, Object> getDirectionData() {
        Map<String, Object> data = new HashMap<>();
        // 嵌入式
        Map<String, String> embedded = new HashMap<>();
        embedded.put("基础知识", "嵌入式原理、C/C++");
        embedded.put("学习硬件", "单片机、传感器");
        embedded.put("RTOS学习", "实时操作系统应用");
        embedded.put("项目实践", "智能家居、物联网设备");
        data.put("嵌入式开发", embedded);
        // 后台
        Map<String, String> backend = new HashMap<>();
        backend.put("编程语言", "Java、Python、Node.js");
        backend.put("数据库", "MySQL、MongoDB");
        backend.put("框架技术", "Spring、Django");
        backend.put("微服务", "微服务架构、RESTful API");
        data.put("后台开发", backend);
        // 前端
        Map<String, String> frontend = new HashMap<>();
        frontend.put("基础技术", "HTML/CSS/JavaScript");
        frontend.put("框架", "React、Vue.js");
        frontend.put("响应式", "移动端适配");
        frontend.put("跨平台", "React Native、Flutter");
        data.put("前端开发", frontend);
        // 安卓
        Map<String, String> android = new HashMap<>();
        android.put("语言", "Java、Kotlin");
        android.put("SDK", "Android开发工具");
        android.put("布局", "界面设计");
        android.put("存储", "SQLite、Room");
        data.put("安卓开发", android);
        return data;
    }

    @Override
    public Map<String, Object> getDirectionStudyPath() {
        Map<String, Object> path = new HashMap<>();
        path.put("嵌入式开发", "1. 学C/C++ → 2. 掌握单片机 → 3. 学RTOS → 4. 做物联网项目");
        path.put("后台开发", "1. 学Java/Python → 2. 练MySQL → 3. 用Spring → 4. 理解微服务");
        path.put("前端开发", "1. 精通HTML/CSS/JS → 2. 学Vue/React → 3. 做响应式设计 → 4. 试跨平台");
        path.put("安卓开发", "1. 学Java/Kotlin → 2. 用Android SDK → 3. 练界面设计 → 4. 发布优化");
        return path;
    }

    @Override
    public boolean updatePassword(String studentId, String oldPwd, String newPwd) {
        // 原密码校验
        Student student = studentMapper.selectOne(new QueryWrapper<Student>()
                .eq("student_id", studentId)
                .eq("password", oldPwd));
        if (student == null) {
            throw new RuntimeException("原密码错误");
        }
        // 新密码长度校验
        if (newPwd.length() <= 6) {
            throw new RuntimeException("新密码必须大于6位");
        }
        student.setPassword(newPwd);
        return studentMapper.updateById(student) > 0;
    }
}

