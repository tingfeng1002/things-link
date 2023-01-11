package com.thingslink.transport.mqtt.session;

import com.thingslink.DeviceProfile;
import com.thingslink.session.DeviceSessionId;
import com.thingslink.transport.session.DeviceAwareSessionContext;
import com.thingslink.transport.session.TransportDeviceSessionId;
import io.netty.channel.ChannelHandlerContext;

/**
 * mqtt  device session context
 * @author wang xiao
 * date 2022/12/27
 */
public class MqttDeviceSessionCtx extends DeviceAwareSessionContext {

    private DeviceProfile deviceProfile;

    private ChannelHandlerContext channelHandlerContext;


    public DeviceSessionId initSession(){
        this.deviceSessionId = new TransportDeviceSessionId(deviceProfile.getDeviceId(), deviceProfile.getModelId());
        return this.deviceSessionId;
    }


    public DeviceProfile getDeviceProfile() {
        return deviceProfile;
    }

    public void setDeviceProfile(DeviceProfile deviceProfile) {
        this.deviceProfile = deviceProfile;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }
}
