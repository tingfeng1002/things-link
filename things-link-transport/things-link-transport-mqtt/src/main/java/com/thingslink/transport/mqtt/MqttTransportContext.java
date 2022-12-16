package com.thingslink.transport.mqtt;

import com.thingslink.transport.TransportContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * mqtt transport context
 *
 * @author wang xiao
 * date 2022/12/16
 */
@Component
@ConditionalOnExpression("'${transport.mqtt.enabled}'=='true'")
public class MqttTransportContext extends TransportContext {
}
