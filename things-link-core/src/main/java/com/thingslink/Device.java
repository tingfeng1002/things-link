package com.thingslink;

/**
 * 设备
 * @author wang xiao
 * date 2022/12/1
 */
public interface Device {

    /**
     * 获取设备id
     * @return  EntityId
     */
    EntityId getDeviceId();



    /**
     * 获取产品id
     * @return  EntityId
     */
    EntityId  getProductId();
}
