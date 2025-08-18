package com.project.laboratory.common.expection;
import com.project.laboratory.common.result.Result;
import com.project.laboratory.common.result.ResultConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * 统一处理控制器层抛出的异常，返回标准 Result 格式
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.error("业务异常: {}", e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数校验异常 (MethodArgumentNotValidException)
     * 用于校验 @RequestBody 参数
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        log.error("参数校验失败: {}", errors);
        return Result.fail(ResultConstant.INVALID_PARAM.getCode(), "参数校验失败")
                .put("details", errors);
    }

    /**
     * 处理参数校验异常 (BindException)
     * 用于校验 @RequestParam 和 @PathVariable 参数
     */
    @ExceptionHandler(BindException.class)
    public Result<?> handleBindException(BindException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        log.error("参数绑定失败: {}", errors);
        return Result.fail(ResultConstant.INVALID_PARAM.getCode(), "参数绑定失败")
                .put("details", errors);
    }

    /**
     * 处理 HTTP 请求体解析异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("请求体解析失败: {}", e.getMessage());
        return Result.fail(ResultConstant.INVALID_PARAM.getCode(), "请求体格式错误");
    }

    /**
     * 处理 SQL 异常
     */
    @ExceptionHandler(SQLException.class)
    public Result<?> handleSQLException(SQLException e) {
        log.error("数据库操作失败: {}", e.getMessage(), e);
        return Result.fail(ResultConstant.DATABASE_ERROR.getCode(), "数据库操作失败");
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public Result<?> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常: {}", e.getMessage(), e);
        return Result.fail(ResultConstant.NULL_POINTER_EXCEPTION.getCode(), "空指针异常");
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<?> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常: {}", e.getMessage(), e);
        return Result.fail(ResultConstant.FAIL.getCode(), "操作失败: " + e.getMessage());
    }

    /**
     * 处理系统异常 (兜底)
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("系统内部错误: {}", e.getMessage(), e);
        return Result.fail(ResultConstant.SYSTEM_ERROR.getCode(), "系统内部错误");
    }
}

   

