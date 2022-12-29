package com.thingslink.transport.mqtt;

import com.thingslink.transport.TransportService;
import com.thingslink.transport.limit.TransportLimitService;
import com.thingslink.transport.mqtt.session.MqttDeviceSessionCtx;
import com.thingslink.transport.session.DeviceSessionListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.UUID;

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


    private final UUID mqttTransportHandleId;


    final MqttDeviceSessionCtx deviceSessionCtx;

    volatile InetSocketAddress address;

    public MqttTransportHandler(MqttTransportContext transportContext) {
        this.transportContext = transportContext;
        this.transportService = transportContext.getTransportService();
        this.transportLimitService = transportContext.getTransportLimitService();
        this.deviceSessionCtx = new MqttDeviceSessionCtx();
        this.mqttTransportHandleId = UUID.randomUUID();
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        transportContext.channelRegistered();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        transportContext.channelUnregistered();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        if (Objects.isNull(address)){
            address = getSocketAddress(ctx);
        }
        try {
            if (msg instanceof MqttMessage){
                MqttMessage mqttMessage = (MqttMessage) msg;
                if (mqttMessage.decoderResult().isSuccess()) {
                    processMqttMessage(ctx, mqttMessage);
                }else {
                    logger.error("[{}] mqtt message decoder is fail,case: {}",mqttTransportHandleId,mqttMessage.decoderResult().cause());
                    ctx.close();
                }
            }else {
                logger.error("[{}] received non mqtt message: {}",mqttTransportHandleId,msg.getClass().getSimpleName());
                ctx.close();
            }
        }finally {
            ReferenceCountUtil.safeRelease(msg);
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
     * todo transport service process disconnect
     */
    private void disConnect() {
        if (deviceSessionCtx.isConnected()){
            transportService.unregisterSession(deviceSessionCtx.getDeviceSessionId());
            deviceSessionCtx.disConnect();
        }
    }


    /**
     * 获取地址
     * @param channelHandlerContext channel handler context
     * @return InetSocketAddress
     */
    private InetSocketAddress getSocketAddress(ChannelHandlerContext channelHandlerContext) {
        var inetSocketAddress = channelHandlerContext.channel().attr(MqttTransportProperties.ADDRESS).get();
        if (Objects.isNull(inetSocketAddress)){
            return (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
        }
        return inetSocketAddress;
    }


    /**
     * 处理mqtt message
     * @param channelHandlerContext channel handler context
     * @param mqttMessage mqtt message
     */
    private void processMqttMessage(ChannelHandlerContext channelHandlerContext,MqttMessage mqttMessage){}
}
