package com.thingslink;

import java.io.Serializable;

/**
 * 实体 接口类
 * @author wang xiao
 * date 2022/12/8
 */
public interface Things extends Serializable {

    /**
     * 获取实体id
     * @return ThingsId
     */
    ThingsId getThingsId();
}
