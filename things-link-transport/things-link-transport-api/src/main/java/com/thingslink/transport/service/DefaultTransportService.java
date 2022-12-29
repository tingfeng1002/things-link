package com.thingslink.transport.service;

import com.thingslink.session.DeviceSessionId;
import com.thingslink.transport.TransportService;
import com.thingslink.transport.session.DeviceSessionListener;
import com.thingslink.transport.session.TransportDeviceSessionInfo;
import org.springframework.stereotype.Service;

/**
 * transport service implementation
 * @author wang xiao
 * date 2022/12/28
 */
@Service
public class DefaultTransportService implements TransportService {



    @Override
    public void registerSession(TransportDeviceSessionInfo sessionInfo, DeviceSessionListener sessionListener) {

    }

    @Override
    public void unregisterSession(DeviceSessionId deviceSessionId) {

    }

    @Override
    public void reportActivity(DeviceSessionId deviceSessionId) {

    }
}
