package com.project.laboratory.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;

    public Result<T> put(String key, Object value) {
        if (this.data == null) {
            this.data = (T) new HashMap<String, Object>();
        }
        if (this.data instanceof Map) {
            ((Map<String, Object>) this.data).put(key, value);
        }
        return this;
    }


    public static Result<?> success(){
        return new Result<>(ResultConstant.SUCCESS.getCode(),ResultConstant.SUCCESS.getMessage(),null);
    }

    public static <T> Result<T> success(T data){
        return new Result<>(ResultConstant.SUCCESS.getCode(), ResultConstant.SUCCESS.getMessage(),data);
    }

    public static <T> Result<T> success(T data,String message){
        return new Result<>(ResultConstant.SUCCESS.getCode(),message,data);
    }

    public static <T> Result<T> success(String message){
        return new Result<>(ResultConstant.SUCCESS.getCode(),message,null);
    }

    public static <T> Result<T> fail(){
        return new Result<>(ResultConstant.FAIL.getCode(),ResultConstant.FAIL.getMessage(),null);
    }

    public static <T> Result<T> fail(Integer code){
        return new Result<>(code,"fail",null);
    }

    public static <T> Result<T> fail(Integer code,String message){
        return new Result<>(code,message,null);
    }

}