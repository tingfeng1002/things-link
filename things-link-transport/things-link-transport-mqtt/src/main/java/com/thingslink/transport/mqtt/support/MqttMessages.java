package com.thingslink.transport.mqtt.support;

import io.netty.handler.codec.mqtt.*;

import static io.netty.handler.codec.mqtt.MqttMessageType.CONNACK;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_MOST_ONCE;

/**
 * mqtt message support
 * @author wang xiao
 * date 2023/1/10
 */
public interface MqttMessages {


    /**
     *  create a new mqtt connect ack message
     * @param returnCode return code
     * @param isCleanSession 是否清除会话
     * @return MqttConnAckMessage
     */
    static MqttConnAckMessage createMqttConnAckMsg(MqttConnectReturnCode returnCode, boolean isCleanSession) {
        MqttFixedHeader mqttFixedHeader =
                new MqttFixedHeader(CONNACK, false, AT_MOST_ONCE, false, 0);
        MqttConnAckVariableHeader mqttConnAckVariableHeader =
                new MqttConnAckVariableHeader(returnCode, isCleanSession);
        return new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
    }
}
