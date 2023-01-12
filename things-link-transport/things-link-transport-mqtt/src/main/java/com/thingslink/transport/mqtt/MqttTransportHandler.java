package com.thingslink.transport.mqtt;

import com.thingslink.DeviceProfile;
import com.thingslink.session.SessionEvent;
import com.thingslink.transport.TransportService;
import com.thingslink.transport.TransportServiceCallback;
import com.thingslink.transport.auth.MqttBaseConnectReqMsg;
import com.thingslink.transport.auth.ValidateDeviceConnectRespMsg;
import com.thingslink.transport.mqtt.session.MqttDeviceSessionCtx;
import com.thingslink.transport.mqtt.support.MqttMessages;
import com.thingslink.transport.session.DeviceSessionListener;
import com.thingslink.util.CastUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static io.netty.handler.codec.mqtt.MqttConnectReturnCode.CONNECTION_ACCEPTED;
import static io.netty.handler.codec.mqtt.MqttConnectReturnCode.CONNECTION_REFUSED_NOT_AUTHORIZED;
import static io.netty.handler.codec.mqtt.MqttMessageType.PINGRESP;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_MOST_ONCE;

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
        logger.trace("[{}]Channel operation complete ,will be closed",mqttTransportHandleId);
        disConnect();
    }

    /**
     * disconnect
     */
    private void disConnect() {
        if (checkConnected()){
            transportService.processSessionEvent(deviceSessionCtx.getDeviceSessionId(), SessionEvent.DISCONNECT, null);
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
    private void processMqttMessage(ChannelHandlerContext channelHandlerContext,MqttMessage message){
        if (Objects.isNull(message.fixedHeader())) {
            logger.info("[{}]MQTT message is Invalid,mqtt fixed header is null",mqttTransportHandleId);
            channelHandlerContext.close();
            return;
        }
        deviceSessionCtx.setChannelHandlerContext(channelHandlerContext);
        var mqttMessageType = message.fixedHeader().messageType();
        switch (mqttMessageType){
            case CONNECT ->  processMqttConnectMessage(channelHandlerContext, CastUtil.cast(message));
            case DISCONNECT -> {
                disConnect();
                channelHandlerContext.close();
            }
            case PINGREQ -> processMqttPingReqMsg(channelHandlerContext);
            case PUBLISH -> processMqttPublishMessage(channelHandlerContext,CastUtil.cast(message));
            case PUBACK -> processMqttPubAckMessage(channelHandlerContext,CastUtil.cast(message));
            case SUBSCRIBE -> processMqttSubscribeMessage(channelHandlerContext,CastUtil.cast(message));
            case UNSUBSCRIBE -> processMqttUnSubscribeMessage(channelHandlerContext,CastUtil.cast(message));
            default -> {}
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
        var cleanSession = connectMessage.variableHeader().isCleanSession();
        transportService.processDeviceMqttBasicAuth(mqttConnectRequest, new TransportServiceCallback<>() {
            @Override
            public void onSuccess(ValidateDeviceConnectRespMsg msg) {
                logger.trace("[{}] mqtt connect success by clientId:{},username:{},password:{}", mqttTransportHandleId,clientIdentifier,userName,password);
                onMqttConnectSuccess(channelHandlerContext,msg,cleanSession);
            }

            @Override
            public void onError(Throwable e) {
                logger.error("[{}] mqtt connect failed by clientId:{},username:{},password:{} why:{}", mqttTransportHandleId,clientIdentifier,userName,password,e.getMessage());
                channelHandlerContext.writeAndFlush(MqttMessages.createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_REFUSED_SERVER_UNAVAILABLE, cleanSession));
                channelHandlerContext.close();
            }
        });
    }


    /**
     * mqtt connect success callback
     * @param channelHandlerContext channel handler context
     * @param msg respMsg
     */
    private void onMqttConnectSuccess (ChannelHandlerContext channelHandlerContext,ValidateDeviceConnectRespMsg msg,boolean cleanSession) {
        var mqttClientId = msg.clientId();
        Optional<DeviceProfile> deviceProfile = msg.deviceProfile();
        deviceProfile.ifPresentOrElse(profile->{
            transportContext.onAuthSuccess(address);
            deviceSessionCtx.setDeviceProfile(profile);
            var sessionId = deviceSessionCtx.initSession();
            transportService.processSessionEvent(sessionId, SessionEvent.CONNECT, new TransportServiceCallback<Void>() {
                @Override
                public void onSuccess(Void msg) {
                    deviceSessionCtx.connect();
                    transportService.registerSession(sessionId,MqttTransportHandler.this);
                    channelHandlerContext.writeAndFlush(MqttMessages.createMqttConnAckMsg(CONNECTION_ACCEPTED, cleanSession));
                    logger.debug("[{}] Client :{} connected!", mqttTransportHandleId,mqttClientId);
                }
                @Override
                public void onError(Throwable e) {
                    logger.error("[{}] process mqtt session event failed by clientId:{}, why:{}", mqttTransportHandleId,mqttClientId,e.getMessage());
                    channelHandlerContext.writeAndFlush(MqttMessages.createMqttConnAckMsg(MqttConnectReturnCode.CONNECTION_REFUSED_SERVER_UNAVAILABLE, cleanSession));
                    channelHandlerContext.close();
                }
            });
        },()-> {
            // why deviceProfile is null,maybe device status is disabled or code append error
            logger.trace("[{}] mqtt connect success by clientId:{},but deviceProfile is null, will be close", mqttTransportHandleId,mqttClientId);
            transportContext.onAuthFailure(address);
            channelHandlerContext.writeAndFlush(MqttMessages.createMqttConnAckMsg(CONNECTION_REFUSED_NOT_AUTHORIZED,cleanSession ));
            channelHandlerContext.close();
        });
    }


    /**
     *  处理mqtt ping request message
     * @param channelHandlerContext channel handler context
     */
    private void processMqttPingReqMsg(ChannelHandlerContext channelHandlerContext){
        if (checkConnected()) {
            channelHandlerContext.writeAndFlush(MqttMessages.createMqttPingRespMessage());
            transportService.reportActivity(deviceSessionCtx.getDeviceSessionId());
        }
    }


    /**
     *  check device connect status
     * @return boolean
     */
    private boolean checkConnected(){
        return deviceSessionCtx.isConnected();
    }


    /**
     * 处理 publish message
     * @param channelHandlerContext channel handler context
     * @param publishMessage publish message
     */
    private void processMqttPublishMessage (ChannelHandlerContext channelHandlerContext,MqttPublishMessage publishMessage){

    }


    /**
     * 处理 publish ack message
     * @param channelHandlerContext channel handler context
     * @param pubAckMessage pubAck message
     */
    private void processMqttPubAckMessage (ChannelHandlerContext channelHandlerContext,MqttPubAckMessage pubAckMessage){

    }


    /**
     * 处理 subscribe message
     * @param channelHandlerContext channel handler context
     * @param subscribeMessage subscribe message
     */
    private void processMqttSubscribeMessage (ChannelHandlerContext channelHandlerContext,MqttSubscribeMessage subscribeMessage){

    }


    /**
     * 处理 unsubscribe message
     * @param channelHandlerContext channel handler context
     * @param unsubscribeMessage unsubscribe message
     */
    private void processMqttUnSubscribeMessage (ChannelHandlerContext channelHandlerContext,MqttUnsubscribeMessage unsubscribeMessage){

    }

}
