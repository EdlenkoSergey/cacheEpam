package com.epam.cache;

import com.epam.cache.evict.EvictStrategy;
import java.util.function.Function;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class LoadingCacheTest {
    private final EvictStrategy<String, Integer> evictStrategy = Mockito.mock(EvictStrategy.class);
    private final Function<String, Integer> loader = Mockito.mock(Function.class);

    @Test
    public void shouldPutEntryToCache() {
        LoadingCache<String, Integer> cache = new LoadingCache<>(10, evictStrategy, loader);
        Integer expectedValue = 1;
        String key = "1";
        cache.put(key, expectedValue);
        assertEquals(expectedValue, cache.get(key));
    }

    @Test
    public void shouldLoadIfNotFoundValueInCache() {
        LoadingCache<String, Integer> cache = new LoadingCache<>(10, evictStrategy, loader);
        int expectedValue = 1;
        String key = "1";
        when(loader.apply(key)).thenReturn(expectedValue);
        assertEquals(expectedValue, cache.get(key).intValue());
    }

    @Test
    public void shouldApplyEvictionPolicyOnGetFromCache() {
        LoadingCache<String, Integer> cache = new LoadingCache<>(10, evictStrategy, loader);
        int expectedValue = 1;
        String key = "1";
        cache.put(key, expectedValue);
        cache.get(key);
        verify(evictStrategy).applyEvictionPolicy(cache, key);
        assertEquals(expectedValue, cache.get(key).intValue());
    }

    @Test
    public void shouldEvictWhenDoesntFitCacheCapacity() {
        LoadingCache<String, Integer> cache = new LoadingCache<>(1, evictStrategy, loader);
        int expectedValue = 1;
        String key = "1";
        cache.put(key, expectedValue);
        cache.put("2", 2);
        verify(evictStrategy, times(2)).evict(cache);
        assertEquals(expectedValue, cache.get(key).intValue());
    }

    @Test
    public void shouldReturnSizeOfCache() {
        LoadingCache<String, Integer> cache = new LoadingCache<>(10, evictStrategy, loader);
        int expectedSize = 1;
        String key = "1";
        cache.put(key, 1);
        assertEquals(expectedSize, cache.size());
    }

    @Test
    public void shouldRemoveValueFromCacheByIndex() {
        LoadingCache<String, Integer> cache = new LoadingCache<>(10, evictStrategy, loader);
        String key = "1";
        cache.put(key, 1);
        cache.remove(0);
        when(loader.apply(key)).thenReturn(1);
        cache.get(key);
        verify(loader).apply(key);
    }

    @Test
    public void shouldRemoveValueFromCacheByKey() {
        LoadingCache<String, Integer> cache = new LoadingCache<>(10, evictStrategy, loader);
        String key = "1";
        cache.put(key, 1);
        cache.remove(key);
        when(loader.apply(key)).thenReturn(1);
        cache.get(key);
        verify(loader).apply(key);
    }
}
