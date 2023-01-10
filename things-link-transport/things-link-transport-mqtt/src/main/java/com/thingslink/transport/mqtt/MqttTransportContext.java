package com.thingslink.transport.mqtt;

import com.thingslink.transport.TransportContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * mqtt transport context
 *
 * @author wang xiao
 * date 2022/12/16
 */
@Component
@ConditionalOnExpression("'${transport.mqtt.enabled}'=='true'")
public class MqttTransportContext extends TransportContext {


    private final AtomicInteger connectionsCounter = new AtomicInteger();

    public void channelRegistered() {
        connectionsCounter.incrementAndGet();
    }

    public void channelUnregistered() {
        connectionsCounter.decrementAndGet();
    }


    public boolean checkAddress(InetSocketAddress address){
        return transportLimitService.checkAddress(address);
    }

    public void onAuthSuccess(InetSocketAddress address) {
        transportLimitService.onAuthSuccess(address);
    }

    public void onAuthFailure(InetSocketAddress address) {
        transportLimitService.onAuthFailure(address);
    }
}
