package com.thingslink.result;

import java.io.Serializable;

/**
 * 返回结果
 * @author wang xiao
 * date 2022/12/12
 */
public class Result<T> implements Serializable {


    private Boolean success;

    private Integer code;

    private String message;

    private T data ;

    private Result(){}


    /**
     * success
     * @return  Result
     */
    public static <T> Result<T> ok() {
        Result<T> r = new Result<>();
        r.setSuccess(ResultCode.SUCCESS.isSuccess());
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        return r;
    }

    /**
     * error
     * @return  Result
     */
    public static <T> Result<T> error() {
        Result<T> r = new Result<>();
        r.setSuccess(ResultCode.UNKNOWN_ERROR.isSuccess());
        r.setCode(ResultCode.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCode.UNKNOWN_ERROR.getMessage());
        return r;
    }


    /**
     * data
     * @param data T value
     * @return Result
     */
    public Result<T> data(T data) {
        this.setData(data);
        return this;
    }


    /**
     * message
     * @param message String message
     * @return Result
     */
    public Result<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * code
     * @param code Integer code
     * @return Result
     */
    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * success
     * @param success boolean success
     * @return Result
     */
    public Result<T> success(Boolean success) {
        this.setSuccess(success);
        return this;
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
