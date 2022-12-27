package com.thingslink.transport.mqtt;

import com.thingslink.transport.TransportService;
import com.thingslink.transport.limit.TransportLimitService;
import com.thingslink.transport.mqtt.session.MqttDeviceSessionCtx;
import com.thingslink.transport.session.DeviceSessionListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mqtt transport handler
 *
 * @author wang xiao
 * date 2022/12/16
 */
public class MqttTransportHandler extends ChannelInboundHandlerAdapter implements GenericFutureListener<Future<? super Void>>, DeviceSessionListener {

    private final Logger logger = LoggerFactory.getLogger(MqttTransportHandler.class);
    private final TransportLimitService transportLimitService;

    private final TransportService transportService;

    private final MqttTransportContext transportContext;


    final MqttDeviceSessionCtx deviceSessionCtx;

    public MqttTransportHandler(MqttTransportContext transportContext) {
        this.transportContext = transportContext;
        this.transportService = transportContext.getTransportService();
        this.transportLimitService = transportContext.getTransportLimitService();
        this.deviceSessionCtx = new MqttDeviceSessionCtx();
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        transportContext.channelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        transportContext.channelRegistered();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof MqttMessage){

        }else {
            logger.error("");
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    @Override
    public void operationComplete(Future<? super Void> future) {
        logger.trace("Channel operation complete ,will be closed");
        disConnect();
    }

    /**
     * disconnect
     */
    private void disConnect() {
        if (deviceSessionCtx.isConnected()){

            deviceSessionCtx.disConnect();
        }
    }
}
