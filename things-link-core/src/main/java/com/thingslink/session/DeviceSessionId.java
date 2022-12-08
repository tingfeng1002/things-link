package com.thingslink.session;

import com.thingslink.ThingsId;

/**
 * 设备 session id
 * @author wang xiao
 * date 2022/12/8
 */
public interface DeviceSessionId {

    /**
     * 获取 设备id
     * @return ThingsId
     */
    ThingsId getDeviceId();


    /**
     * 获取模型id
     * @return ThingsId
     */
    ThingsId getModelId();
}
