package com.thingslink.result;

/**
 * 结果状态码
 * @author wang xiao
 * date 2022/12/12
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(true,0,"成功"),

    /**
     * 未知异常
     */
    UNKNOWN_ERROR(false,5000,"未知错误"),

    /**
     * 参数错误
     */
    PARAM_ERROR(false,5002,"参数错误")
    ;


    private final boolean success;

    private final Integer code;

    private final String message;

    ResultCode(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
