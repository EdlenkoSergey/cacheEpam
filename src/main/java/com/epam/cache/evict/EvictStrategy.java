package com.epam.cache.evict;

import com.epam.cache.Cache;

public interface EvictStrategy<K, V> {
    void evict(Cache<K, V> cache);

    default void applyEvictionPolicy(Cache<K, V> cache, K key) {
    }
}
