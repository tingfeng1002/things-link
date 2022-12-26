package com.thingslink.transport.session;

import com.thingslink.session.DeviceSession;
import com.thingslink.session.DeviceSessionId;

import java.util.UUID;

/**
 * Device Session context
 * @author wang xiao
 * date 2022/12/26
 */
public abstract class DeviceAwareSessionContext implements DeviceSession {

    private DeviceSessionId deviceSessionId;

    private long lastConnectTime;

    private UUID transportChannelId;

    private volatile boolean connected;

    

    @Override
    public DeviceSessionId getDeviceSessionId() {
        return deviceSessionId;
    }

    @Override
    public long getLastConnectTime() {
        return lastConnectTime;
    }

    @Override
    public UUID getTransportChannelId() {
        return transportChannelId;
    }


    public void setDeviceSessionId(DeviceSessionId deviceSessionId) {
        this.deviceSessionId = deviceSessionId;
    }

    public void setLastConnectTime(long lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }

    public void setTransportChannelId(UUID transportChannelId) {
        this.transportChannelId = transportChannelId;
    }


    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void  disConnect() {
        this.connected = false;
    }

    public void  connect() {
        this.connected = true;
    }
}
