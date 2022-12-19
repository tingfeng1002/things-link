package com.thingslink;

import java.util.Optional;

/**
 * 异常信息
 *
 * @author wang xiao
 * date 2022/12/12
 */
public class ThingsLinkException extends RuntimeException {


    private Optional<Integer> code;


    public ThingsLinkException(Integer code, String message) {
        super(message);
        this.code = Optional.ofNullable(code);
    }

    public ThingsLinkException(String message) {
        super(message);
    }

    public ThingsLinkException(String message, Throwable cause) {
        super(message, cause);
    }


    public Optional<Integer> getCode() {
        return code;
    }

    /**
     * 异常的昂贵且无用的堆栈跟踪 一般建议 this
     *
     * @return Throwable
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
