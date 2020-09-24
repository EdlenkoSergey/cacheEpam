package com.epam.cache.evict;

import com.epam.cache.Cache;
import java.util.concurrent.ThreadLocalRandom;

public class RandomEvicter<K, V> implements EvictStrategy<K, V> {
    @Override
    public void evict(Cache<K, V> cache) {
        cache.remove(ThreadLocalRandom.current().nextInt(cache.size()));
    }
}
