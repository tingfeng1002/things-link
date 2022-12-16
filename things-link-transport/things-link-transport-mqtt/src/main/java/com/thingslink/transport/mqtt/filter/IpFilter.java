package com.thingslink.transport.mqtt.filter;

import com.thingslink.transport.mqtt.MqttTransportContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ipfilter.AbstractRemoteAddressFilter;

import java.net.InetSocketAddress;

/**
 * 连接ip 限制
 *
 * @author wang xiao
 * date 2022/12/16
 */
public class IpFilter extends AbstractRemoteAddressFilter<InetSocketAddress> {

    protected MqttTransportContext transportContext;

    public IpFilter(MqttTransportContext transportContext) {
        this.transportContext = transportContext;
    }

    @Override
    protected boolean accept(ChannelHandlerContext channelHandlerContext, InetSocketAddress inetSocketAddress) throws Exception {
        return false;
    }
}
