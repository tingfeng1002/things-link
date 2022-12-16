package com.thingslink.transport.mqtt;

import com.thingslink.transport.TransportServer;
import com.thingslink.transport.TransportType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

/**
 * MQTT transport server
 * @author wang xiao
 * date 2022/12/1
 */
@Service("MqttTransportServer")
@ConditionalOnExpression("'${transport.mqtt.enabled}'=='true'")
public class MqttTransportServer  implements TransportServer {

    private MqttTransportProperties mqttTransportProperties;

    @Override
    public String getServerName() {
        return TransportType.MQTT.name();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
