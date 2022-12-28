package com.thingslink;

/**
 * Service Call back
 *
 * @author wang xiao
 * date 2022/12/16
 */
public interface ServiceCallback<T> {


    ServiceCallback<Void> EMPTY = new ServiceCallback<>() {
        @Override
        public void onSuccess(Void msg) {}

        @Override
        public void onError(Throwable e) {}
    };

    /**
     * 成功
     * @param msg 消息
     */
    void onSuccess(T msg);

    /**
     * 失败
     * @param e 错误信息
     */
    void onError(Throwable e);
}
