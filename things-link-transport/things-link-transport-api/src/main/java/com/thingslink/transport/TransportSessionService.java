package com.thingslink.transport;

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
     */

    void  registerSession(TransportDeviceSessionInfo sessionInfo, DeviceSessionListener);


    /**
     * 移除session
     */
    void unregisterSession();


    /**
     * 活跃
     */
    void  reportActivity();
}
