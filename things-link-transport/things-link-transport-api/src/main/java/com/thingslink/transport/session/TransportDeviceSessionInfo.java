package com.thingslink.transport.session;

import com.thingslink.session.DeviceSessionId;

/**
 * transport device session info
 * @author wang xiao
 * date 2022/12/28
 */
public class TransportDeviceSessionInfo {

    private DeviceSessionId deviceSessionId;

    private long lastConnectTime;

    private long  lastActivityTime;

    public DeviceSessionId getDeviceSessionId() {
        return deviceSessionId;
    }

    public void setDeviceSessionId(DeviceSessionId deviceSessionId) {
        this.deviceSessionId = deviceSessionId;
    }

    public long getLastConnectTime() {
        return lastConnectTime;
    }

    public void setLastConnectTime(long lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }

    public long getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(long lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }
}
