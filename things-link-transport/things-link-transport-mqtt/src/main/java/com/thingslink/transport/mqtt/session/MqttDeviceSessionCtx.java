package com.thingslink.transport.mqtt.session;

import com.thingslink.transport.session.DeviceAwareSessionContext;
import io.netty.channel.ChannelHandlerContext;

/**
 * mqtt  device session context
 * @author wang xiao
 * date 2022/12/27
 */
public class MqttDeviceSessionCtx extends DeviceAwareSessionContext {


    private ChannelHandlerContext channelHandlerContext;


    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }
}
