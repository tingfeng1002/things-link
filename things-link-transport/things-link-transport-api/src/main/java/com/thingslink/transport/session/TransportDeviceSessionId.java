package com.thingslink.transport.session;

import com.thingslink.ThingsId;
import com.thingslink.ThingsType;
import com.thingslink.session.DeviceSessionId;

/**
 * transport session info
 * @author wang xiao
 * date 2022/12/28
 */
public class TransportDeviceSessionId implements DeviceSessionId {

    private final Long deviceId;


    private final Long modelId;


    private final String sessionId;


    public TransportDeviceSessionId(Long deviceId, Long modelId) {
        this.deviceId = deviceId;
        this.modelId = modelId;
        this.sessionId = String.format("%d_%d", modelId,deviceId);
    }

    @Override
    public ThingsId getDeviceId() {
        return ThingsId.createDefaultThingsId(deviceId, ThingsType.DEVICE);
    }

    @Override
    public ThingsId getModelId() {
        return ThingsId.createDefaultThingsId(modelId, ThingsType.MODEL);
    }


    @Override
    public String getSessionId() {
        return sessionId;
    }
}
