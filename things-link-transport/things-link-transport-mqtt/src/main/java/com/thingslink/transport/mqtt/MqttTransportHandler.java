package com.thingslink.transport.mqtt;

import com.thingslink.transport.TransportService;
import com.thingslink.transport.limit.TransportLimitService;
import com.thingslink.transport.session.DeviceSessionListener;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * mqtt transport handler
 *
 * @author wang xiao
 * date 2022/12/16
 */
public class MqttTransportHandler extends ChannelInboundHandlerAdapter implements GenericFutureListener<Future<? super Void>>, DeviceSessionListener {


    private final TransportLimitService transportLimitService;

    private final TransportService transportService;


    public MqttTransportHandler(MqttTransportContext transportContext) {
        this.transportService = transportContext.getTransportService();
        this.transportLimitService = transportContext.getTransportLimitService();
    }

    @Override
    public void operationComplete(Future<? super Void> future) {

    }
}
