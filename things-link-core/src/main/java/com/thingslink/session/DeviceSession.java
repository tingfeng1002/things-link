package com.thingslink.session;

import java.util.UUID;

/**
 * 设备 session
 * @author wang xiao
 * date 2022/12/6
 */
public interface DeviceSession {

    /**
     *  获取 device session id
     * @return DeviceSessionId
     */
    DeviceSessionId getDeviceSessionId();


    /**
     * 获取上一次连接时间
     * @return long time
     */
    long getLastConnectTime();


    /**
     *  获取 传输通道id
     * @return UUID
     */
    UUID getTransportChannelId();
}
