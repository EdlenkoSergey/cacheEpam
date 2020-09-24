package com.epam.cache;

import com.epam.cache.evict.EvictStrategy;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PlainCache<K, V> implements Cache<K, V> {
    protected final ConcurrentSkipListMap<K, V> cache = new ConcurrentSkipListMap<>();
    private Lock lock = new ReentrantLock();
    protected EvictStrategy<K, V> evictStrategy;
    protected int capacity;

    public PlainCache(int capacity, EvictStrategy<K, V> evictStrategy) {
        this.capacity = capacity;
        this.evictStrategy = evictStrategy;
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        evictStrategy.applyEvictionPolicy(this, key);
        return value;
    }

    @Override
    public void put(K key, V value) {
        evictIfNeeded();
        lock.lock();
        evictIfNeeded();
        cache.put(key, value);
        lock.unlock();
    }

    private void evictIfNeeded() {
        if (cache.size() >= capacity) {
            evictStrategy.evict(this);
        }
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public void remove(int index) {
        if (index >= size()) {
            return;
        }
        if (index == 0) {
            cache.remove(cache.firstKey());
        } else if (index == size() - 1) {
            cache.remove(cache.lastKey());
        } else {
            Iterator<Map.Entry<K, V>> iterator = cache.entrySet().iterator();
            int currentIndex = 0;
            while (iterator.hasNext()) {
                iterator.next();
                if (index == currentIndex) {
                    iterator.remove();
                }
                currentIndex++;
            }
        }
    }

    @Override
    public V remove(K key) {
        return cache.remove(key);
    }
}
