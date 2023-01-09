package com.thingslink.transport.auth;

import com.thingslink.DeviceProfile;

/**
 *  设备 连接反馈消息
 * @author wang xiao
 * date 2023/1/9
 */
public class ValidateDeviceConnectRespMsg {

    private final DeviceProfile deviceProfile;

    private final String credentials;

    public ValidateDeviceConnectRespMsg(DeviceProfile deviceProfile, String credentials) {
        this.deviceProfile = deviceProfile;
        this.credentials = credentials;
    }

    public DeviceProfile getDeviceProfile() {
        return deviceProfile;
    }

    public String getCredentials() {
        return credentials;
    }
}
