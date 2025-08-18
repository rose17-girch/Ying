package com.project.laboratory.controller;




import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.project.laboratory.model.dto.Result;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
@ApiResponse(ref = "首页相关接口")
public class HomeController {

    @GetMapping("/lab/intro")
    @ApiOperationSupport(author = "实验室介绍（后续补充内容）")
    public Result<String> getLabIntro() {
        // 后续可替换为真实介绍，或从数据库/OSS读取
        return Result.success("实验室专注于嵌入式、后台、前端、安卓四大方向研发，致力于培养技术创新人才！");
    }

    @GetMapping("/direction/intro")
    @ApiOperationSupport(author = "四大方向介绍")
    public Result<Map<String, String>> getDirectionIntro() {
        Map<String, String> intro = new HashMap<>();
        intro.put("嵌入式开发", "为特定硬件平台开发专用软件，用于微控制器、单片机、IoT设备等。");
        intro.put("后台开发", "负责服务器端逻辑处理、数据库管理和业务功能实现，是系统的“大脑”。");
        intro.put("前端开发", "关注用户界面设计与实现，通过HTML/CSS/JavaScript转化视觉设计为交互体验。");
        intro.put("安卓开发", "为Android系统创建移动应用，覆盖智能手机、平板等设备。");
        return Result.success(intro);
    }

    @GetMapping("/awards")
    @ApiOperationSupport(author = "实验室奖项介绍")
    public Result<String[]> getAwards() {
        String[] awards = {
                "全国仿真创新设计大赛国一",
                "全国大学生计算机设计大赛西北赛区二等奖",
                "数学建模大赛校赛一等奖",
                "互联网+校赛二等奖",
                "24年TI杯电子设计竞赛省一",
                "蓝桥杯C/C++省一",
                "嵌入式系统专题邀请赛（英特尔杯）三等奖"
        };
        return Result.success(awards);
    }
}
