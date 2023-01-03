package com.thingslink.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author wang xiao
 * date 2023/1/3
 */
public class RemoveEldestLinkedHashMap<K, V> extends LinkedHashMap<K, V> {

    final long maxEntries;

    final BiConsumer<K,V> removeConsumer;

    public RemoveEldestLinkedHashMap( long maxEntries, BiConsumer<K, V> removeConsumer) {
        this.maxEntries = maxEntries;
        this.removeConsumer = removeConsumer;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        if (size() <= maxEntries){
            return false;
        }
        removeConsumer.accept(eldest.getKey(), eldest.getValue());
        return true;
    }
}
