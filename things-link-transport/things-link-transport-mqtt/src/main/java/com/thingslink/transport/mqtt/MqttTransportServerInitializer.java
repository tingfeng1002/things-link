package com.thingslink.transport.mqtt;

import com.thingslink.transport.mqtt.filter.IpFilter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;

/**
 * channel handler
 *
 * @author wang xiao
 * date 2022/12/16
 */
public class MqttTransportServerInitializer extends ChannelInitializer<SocketChannel> {

    protected MqttTransportContext transportContext;

    protected Integer payloadSize;

    public MqttTransportServerInitializer(MqttTransportContext transportContext, Integer payloadSize) {
        this.transportContext = transportContext;
        this.payloadSize = payloadSize;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("ipFilter", new IpFilter(transportContext));
        channelPipeline.addLast("decoder", new MqttDecoder(payloadSize));
        channelPipeline.addLast("encoder", MqttEncoder.INSTANCE);
        MqttTransportHandler transportHandler = new MqttTransportHandler();
        socketChannel.closeFuture().addListener(transportHandler);
    }
}
