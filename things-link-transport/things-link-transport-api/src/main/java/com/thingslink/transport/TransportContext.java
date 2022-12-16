package com.thingslink.transport;

import com.thingslink.transport.limit.TransportLimitService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * transport context
 *
 * @author wang xiao
 * date 2022/12/16
 */
public abstract class TransportContext {

    protected TransportService transportService;

    protected TransportLimitService transportLimitService;

    public TransportService getTransportService() {
        return transportService;
    }

    @Autowired
    public void setTransportService(TransportService transportService) {
        this.transportService = transportService;
    }

    public TransportLimitService getTransportLimitService() {
        return transportLimitService;
    }

    @Autowired
    public void setTransportLimitService(TransportLimitService transportLimitService) {
        this.transportLimitService = transportLimitService;
    }
}
