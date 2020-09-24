package com.epam.cache.evict;

import com.epam.cache.Cache;

public class LruEvicter<K, V> implements EvictStrategy<K, V> {
    @Override
    public void evict(Cache<K, V> cache) {
        cache.remove(cache.size() - 1);
    }

    @Override
    public void applyEvictionPolicy(Cache<K, V> cache, K key) {
        V value = cache.remove(key);
        cache.put(key, value);
    }
}
