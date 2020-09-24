package com.epam.cache;

public interface Cache<K, V> {
    V get(K key);

    void put(K key, V value);

    int size();

    void remove(int index);

    V remove(K key);
}
