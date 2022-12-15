package com.thingslink.ts;

/**
 * 时序数据
 * @author wang xiao
 * date 2022/12/13
 */
public interface TsData <P> {


    /**
     * 获取 时间
     * @return long
     */
    long getTs();

    /**
     * 获取设备id
     * @return String
     */
    String getDeviceId();


    /**
     * 获取负载数据
     * @return P payload data
     */
    P getPayload();

}
