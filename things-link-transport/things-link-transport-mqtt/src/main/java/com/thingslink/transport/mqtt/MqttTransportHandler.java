package com.thingslink.transport.mqtt;

import com.thingslink.transport.TransportServiceCallback;
import com.thingslink.transport.auth.MqttBaseConnectReqMsg;
import com.thingslink.transport.TransportService;
import com.thingslink.transport.auth.ValidateDeviceConnectRespMsg;
import com.thingslink.transport.mqtt.session.MqttDeviceSessionCtx;
import com.thingslink.transport.session.DeviceSessionListener;
import com.thingslink.util.CastUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttConnectMessage;
import io.netty.handler.codec.mqtt.MqttConnectPayload;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
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

    private final TransportService transportService;

    private final MqttTransportContext transportContext;


    private final UUID mqttTransportHandleId;


    final MqttDeviceSessionCtx deviceSessionCtx;

    volatile InetSocketAddress address;

    public MqttTransportHandler(MqttTransportContext transportContext) {
        this.transportContext = transportContext;
        this.transportService = transportContext.getTransportService();
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
            if (msg instanceof MqttMessage mqttMessage){
                if (mqttMessage.decoderResult().isSuccess()) {
                    processMessage(ctx, mqttMessage);
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
        logger.trace("[{}]Channel operation complete ,will be closed",mqttTransportHandleId);
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
     * @param message mqtt message
     */
    private void processMessage(ChannelHandlerContext channelHandlerContext,MqttMessage message){
        if (Objects.isNull(message.fixedHeader())) {
            logger.info("[{}]MQTT message is Invalid,mqtt fixed header is null",mqttTransportHandleId);
            channelHandlerContext.close();
            return;
        }
        deviceSessionCtx.setChannelHandlerContext(channelHandlerContext);
        var mqttMessageType = message.fixedHeader().messageType();
        if (Objects.requireNonNull(mqttMessageType).equals( MqttMessageType.CONNECT)) {
            processMqttConnectMessage(channelHandlerContext, CastUtil.cast(message));
        } else {
            processMqttMessage(channelHandlerContext, CastUtil.cast(message));
        }
    }


    /**
     *  处理 connect message
     * @param channelHandlerContext channel handler context
     * @param connectMessage mqtt connect message
     */
    private void processMqttConnectMessage(ChannelHandlerContext channelHandlerContext, MqttConnectMessage connectMessage){
        logger.debug("[{}] process Mqtt Connect message",mqttTransportHandleId);
        processMqttBasicConnect(channelHandlerContext,connectMessage);
    }


    /**
     * 处理 mqtt message
     * @param channelHandlerContext channel handler context
     * @param mqttMessage mqtt message
     */
    private void processMqttMessage(ChannelHandlerContext channelHandlerContext,MqttMessage mqttMessage){

    }

    /**
     * clientId  username password 认证
     * @param channelHandlerContext channel handler context
     * @param connectMessage mqtt connect message
     */
    private void processMqttBasicConnect(ChannelHandlerContext channelHandlerContext, MqttConnectMessage connectMessage){
        MqttConnectPayload payload = connectMessage.payload();
        var  userName = payload.userName();
        var clientIdentifier = payload.clientIdentifier();
        var password = new String(payload.passwordInBytes(), StandardCharsets.UTF_8);
        logger.debug("[{}]Mqtt Connect message,clientId:{},userName:{},password:{}",mqttTransportHandleId,clientIdentifier,userName,password);
        var mqttConnectRequest  = new MqttBaseConnectReqMsg(clientIdentifier,userName,password);
        transportService.processDeviceMqttBasicAuth(mqttConnectRequest, new TransportServiceCallback<>() {
            @Override
            public void onSuccess(ValidateDeviceConnectRespMsg msg) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


}
