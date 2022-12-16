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
     *
     * @throws Exception init maybe throw exception
     */
    void initialize() throws Exception;


    /**
     * 关闭
     *
     * @throws Exception init maybe throw exception
     */
    void stop() throws Exception;
}
