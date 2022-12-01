package com.thingslink;

import com.thingslink.id.EntityId;

/**
 * @author wang xiao
 * date 2022/12/1
 */
public interface Device {

    /**
     * 获取设备id
     * @return 设备id
     */
    EntityId getDeviceId();


    /**
     * 获取产品id
     * @return 产品id
     */
    EntityId getProductId();
}
