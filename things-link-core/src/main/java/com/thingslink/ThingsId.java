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


    /**
     * 创建 默认匿名 things id
     * @param id id
     * @param type thingsType
     * @return ThingsId
     */

    static ThingsId createDefaultThingsId(Long id, ThingsType type){
        return new ThingsId() {
            @Override
            public String getId() {
                return String.valueOf(id);
            }

            @Override
            public ThingsType getThingsType() {
                return type;
            }
        };
    }


    /**
     * 创建 默认匿名 things id
     * @param id id
     * @param type thingsType
     * @return ThingsId
     */
    static ThingsId createDefaultThingsId(String id,ThingsType type){
        return new ThingsId() {
            @Override
            public String getId() {
                return id;
            }

            @Override
            public ThingsType getThingsType() {
                return type;
            }
        };
    }
}
