package com.thingslink;

/**
 * @author wang xiao
 * date 2022/12/1
 */
public interface EntityId {

    /**
     * 获取id
     * @return 实体id
     */
    String getId();

    /**
     * 获取类型
     * @return 实体类型
     */
    EntityType getEntityType();
}
