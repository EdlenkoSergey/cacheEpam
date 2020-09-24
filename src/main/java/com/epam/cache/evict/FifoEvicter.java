package com.epam.cache.evict;

import com.epam.cache.Cache;

public class FifoEvicter<K, V> implements EvictStrategy<K, V> {
    @Override
    public void evict(Cache<K, V> cache) {
        cache.remove(0);
    }
}
