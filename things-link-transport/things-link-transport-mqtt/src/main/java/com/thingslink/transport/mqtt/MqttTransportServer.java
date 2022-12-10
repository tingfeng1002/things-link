package com.thingslink.transport.mqtt;

import com.thingslink.transport.TransportServer;
import com.thingslink.transport.TransportType;

/**
 * MQTT transport server
 * @author wang xiao
 * date 2022/12/1
 */
public class MqttTransportServer  implements TransportServer {



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
