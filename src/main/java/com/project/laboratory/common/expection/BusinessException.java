package com.project.laboratory.common.expection;

import com.project.laboratory.common.result.ResultConstant;
import lombok.Getter;

/**
 * 业务异常类
 * 用于在业务逻辑中主动抛出异常，并携带错误码和错误信息
 */
@Getter
public class BusinessException extends RuntimeException {
    private final Integer code;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ResultConstant resultConstant) {
        super(resultConstant.getMessage());
        this.code = resultConstant.getCode();
    }
}