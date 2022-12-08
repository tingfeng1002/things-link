package com.thingslink.transport;

/**
 * @author wang xiao
 * date 2022/12/1
 */
public interface TransportServer {

    /**
     * 获取服务名
     * @return 服务名
     */
    String getServerName();


    /**
     * 初始化
     */
    void initialize();

    /**
     * 启动
     */
    void  start();


    /**
     * 关闭
     */
    void stop();
}
