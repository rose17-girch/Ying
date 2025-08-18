package com.project.laboratory.common.result;

public enum ResultConstant {
    SUCCESS(200, "操作成功"),
    FAIL(400, "操作失败"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    SYSTEM_ERROR(500, "系统内部错误"),
    DATABASE_ERROR(501, "数据库操作失败"),
    NULL_POINTER_EXCEPTION(502, "空指针异常"),
    INVALID_PARAM(503, "参数校验失败");

    private final Integer code;
    private String message;

    private ResultConstant(Integer code){
        this.code = code;
    }

    private ResultConstant(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
    
}

