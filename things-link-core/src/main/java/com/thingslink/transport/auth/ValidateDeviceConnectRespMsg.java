package com.thingslink.transport.auth;

import com.thingslink.DeviceProfile;

import java.util.Optional;

/**
 * 设备 连接反馈消息
 *
 * @author wang xiao
 * date 2023/1/9
 */
public record ValidateDeviceConnectRespMsg(Optional<DeviceProfile> deviceProfile, String credentials) {


    @Override
    public Optional<DeviceProfile> deviceProfile() {
        return deviceProfile;
    }

    @Override
    public String credentials() {
        return credentials;
    }

}
