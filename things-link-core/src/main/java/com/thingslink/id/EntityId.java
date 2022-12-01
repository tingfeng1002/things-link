package com.thingslink.id;

import com.thingslink.EntityType;

/**
 * 实体id
 * @author wang xiao
 * date 2022/12/1
 */
public interface EntityId {

    /**
     * 获取id
     * @return id
     */
    String getId();

    /**
     * 获取实体类型
     * @return entity type
     */
    EntityType getEntityType();
}
