package com.thingslink.cache;

import java.util.Optional;

/**
 * 缓存
 * @author wang xiao
 * date 2022/12/1
 */
public interface TlCache<K,V> {

    /**
     * 存放缓存
     * @param key key
     * @param value value
     */
    void put(K key, V value);


    /**
     * 获取 value
     * @param key key
     * @return Value
     */
    V get(K key);


    /**
     * getIfPresent
     * @param key key
     * @return Optional
     */
    Optional<V> getIfPresent(K key);

    /**
     * 移除 key
     * @param key key
     */
    void remove(K key);

    /**
     * 更改 ttl
     * @param key key
     */
    void ttl(K key);

    /**
     * 清除缓存
     */
    void cleanUp();
}
