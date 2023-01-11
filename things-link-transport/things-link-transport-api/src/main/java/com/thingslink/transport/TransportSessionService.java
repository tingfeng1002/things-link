package com.thingslink.transport;

import com.thingslink.session.DeviceSessionId;
import com.thingslink.session.SessionEvent;
import com.thingslink.transport.session.DeviceSessionListener;

/**
 * transport session  service
 * @author wang xiao
 * date 2022/12/28
 */
public interface TransportSessionService {

    /**
     * 注册session
     * @param deviceSessionId  session info
     * @param sessionListener  session listener
     */
    void  registerSession(DeviceSessionId deviceSessionId, DeviceSessionListener sessionListener);


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


    /**
     * 处理 session event
     * @param deviceSessionId device session id
     * @param event sessionEvent
     * @param callback callback
     */
    void processSessionEvent(DeviceSessionId deviceSessionId, SessionEvent event,TransportServiceCallback<Void> callback);
}
