package com.thingslink.transport;

import com.thingslink.session.DeviceSessionId;
import com.thingslink.transport.session.DeviceSessionListener;
import com.thingslink.transport.session.TransportDeviceSessionInfo;

/**
 * transport session  service
 * @author wang xiao
 * date 2022/12/28
 */
public interface TransportSessionService {

    /**
     * 注册session
     * @param sessionInfo  session info
     * @param sessionListener  session listener
     */
    void  registerSession(TransportDeviceSessionInfo sessionInfo, DeviceSessionListener sessionListener);


    /**
     * 移除session
     * @param deviceSessionId  device session id
     */
    void unregisterSession(DeviceSessionId deviceSessionId);


    /**
     * 活跃
     * @param deviceSessionId device session id
     */
    void  reportActivity(DeviceSessionId deviceSessionId);
}
