package com.epam.cache;

import com.epam.cache.evict.EvictStrategy;
import java.util.function.Function;

public class LoadingCache<K, V> extends PlainCache<K, V> {
    private final Function<K, V> loader;

    public LoadingCache(int capacity, EvictStrategy<K, V> evictStrategy, Function<K, V> loader) {
        super(capacity, evictStrategy);
        this.loader = loader;
    }

    @Override
    public V get(K key) {
        V value = super.get(key);
        if (value == null) {
            value = loader.apply(key);
            put(key, value);
        }
        return value;
    }
}
