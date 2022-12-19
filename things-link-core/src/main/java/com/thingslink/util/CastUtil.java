package com.thingslink.util;

/**
 * 类型 强制转换
 * @author wang xiao
 * date 2022/12/19
 */
public interface CastUtil {

    /**
     * 类型 转换
     * @param object 原始对象
     * @return 目标 对象
     * @param <T> 类型
     */
    @SuppressWarnings("unchecked")
    static <T> T cast(Object object) {
        return (T) object;
    }
}
