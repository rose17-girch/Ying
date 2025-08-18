package com.project.laboratory.interceptor;

import com.alibaba.fastjson2.JSON;
import com.project.laboratory.common.properties.JwtProperties;
import com.project.laboratory.common.result.Result;
import com.project.laboratory.common.result.ResultConstant;
import com.project.laboratory.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    // 定义不需要拦截的路径
    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/favicon.ico",
            "/*.html",
            "/*.js",
            "/*.css",
            "/*.png",
            "/*.jpg",
            "/*.jpeg",
            "/*.gif",
            "/*.svg",
            "/webjars/**"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            // 当前拦截到的不是动态方法，直接放行
            return true;
        }

        // 检查是否为排除路径
        String requestURI = request.getRequestURI();
        if (isExcludedPath(requestURI)) {
            log.debug("排除路径放行: {}", requestURI);
            return true;
        }

        // 1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getTokenName());
        log.info("jwt校验 => {}", token);

        // 2、校验令牌
        try {
            // 3、解析
            if (token != null) {
                Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
                // 可以将用户信息存入request，供后续控制器使用
                request.setAttribute("claims", claims);
                return true;
            }
        } catch (Exception ex) {
            log.error("jwt解析异常: {}", ex.getMessage());
            // 异常处理保持不变
        }

        // 4、校验失败，返回未登录错误
        log.info("jwt校验失败 => {}", token);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 设置HTTP状态码401

        // 使用Result类生成统一格式的JSON响应
        response.getWriter().write(JSON.toJSONString(
                Result.fail(ResultConstant.UNAUTHORIZED.getCode(), ResultConstant.UNAUTHORIZED.getMessage())
        ));
        return false;
    }

    // 判断是否为排除路径
    private boolean isExcludedPath(String requestURI) {
        return EXCLUDED_PATHS.stream().anyMatch(pattern ->
                requestURI.matches(pattern.replace("**", ".*"))
        );
    }
}