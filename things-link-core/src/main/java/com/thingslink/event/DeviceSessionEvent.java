package com.thingslink.event;

import com.thingslink.ThingsId;

/**
 * 设备 session 时间
 * @author wang xiao
 * date 2022/12/8
 */
public record DeviceSessionEvent(ThingsId entityId, EventType eventType) implements TlEvent{

    @Override
    public ThingsId getEntityId() {
        return entityId;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }
}
