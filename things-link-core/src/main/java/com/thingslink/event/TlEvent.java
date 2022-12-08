package com.thingslink.event;

import com.thingslink.ThingsId;

import java.io.Serializable;

/**
 * 事件
 * @author wang xiao
 * date 2022/12/1
 */
public interface TlEvent extends Serializable {

    /**
     * 获取 实体id
     * @return EntityId
     */
    ThingsId getEntityId();


    /**
     * 获取 事件类型
     * @return EventType
     */
    EventType getEventType();
}
