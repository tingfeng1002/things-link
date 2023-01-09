package com.thingslink.transport;

import com.thingslink.transport.auth.MqttBaseConnectReqMsg;
import com.thingslink.transport.auth.ValidateDeviceConnectRespMsg;

/**
 * transport service
 * @author wang xiao
 * date 2022/12/16
 */
public interface TransportService extends TransportSessionService{


    /**
     * process mqtt connect request
     * @param mqttConnectRequest mqtt connect request msg
     * @param callback transport callback
     */
    void processDeviceMqttBasicAuth(MqttBaseConnectReqMsg mqttConnectRequest, TransportServiceCallback<ValidateDeviceConnectRespMsg> callback);

}
