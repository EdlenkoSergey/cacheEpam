package com.epam.cache;

import com.epam.cache.evict.EvictStrategy;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SuppressWarnings("unchecked")
public class PlainCacheTest {
    private final EvictStrategy<String, Integer> evictStrategy = Mockito.mock(EvictStrategy.class);

    @Test
    public void shouldPutEntryToCache() {
        PlainCache<String, Integer> cache = new PlainCache<>(10, evictStrategy);
        Integer expectedValue = 1;
        String key = "1";
        cache.put(key, expectedValue);
        assertEquals(expectedValue, cache.get(key));
    }

    @Test
    public void shouldApplyEvictionPolicyOnGetFromCache() {
        PlainCache<String, Integer> cache = new PlainCache<>(10, evictStrategy);
        int expectedValue = 1;
        String key = "1";
        cache.put(key, expectedValue);
        cache.get(key);
        verify(evictStrategy).applyEvictionPolicy(cache, key);
        assertEquals(expectedValue, cache.get(key).intValue());
    }

    @Test
    public void shouldEvictWhenDoesntFitCacheCapacity() {
        PlainCache<String, Integer> cache = new PlainCache<>(1, evictStrategy);
        int expectedValue = 1;
        String key = "1";
        cache.put(key, expectedValue);
        cache.put("2", 2);
        verify(evictStrategy, times(1)).evict(cache);
        assertEquals(expectedValue, cache.get(key).intValue());
    }

    @Test
    public void shouldReturnSizeOfCache() {
        PlainCache<String, Integer> cache = new PlainCache<>(10, evictStrategy);
        int expectedSize = 1;
        String key = "1";
        cache.put(key, 1);
        assertEquals(expectedSize, cache.size());
    }

    @Test
    public void shouldRemoveValueFromCacheByIndex() {
        PlainCache<String, Integer> cache = new PlainCache<>(10, evictStrategy);
        String key = "1";
        cache.put(key, 1);
        cache.remove(0);
        assertNull(cache.get(key));
    }

    @Test
    public void shouldRemoveValueFromCacheByKey() {
        PlainCache<String, Integer> cache = new PlainCache<>(10, evictStrategy);
        String key = "1";
        cache.put(key, 1);
        cache.remove(key);
        assertNull(cache.get(key));
    }
}
