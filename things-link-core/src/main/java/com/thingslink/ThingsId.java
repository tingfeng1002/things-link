package com.thingslink;

/**
 * 实体id
 * @author wang xiao
 * date 2022/12/1
 */
public interface ThingsId {

    /**
     * 获取id
     * @return 实体id
     */
    String getId();

    /**
     * 获取类型
     * @return 实体类型
     */
    ThingsType getThingsType();
}
