package com.epam.cache.evict;

import com.epam.cache.Cache;
import com.epam.cache.PlainCache;
import java.util.Iterator;
import java.util.Map;
import org.junit.Test;

import static com.epam.cache.ReflectionUtils.getPrivateField;
import static org.junit.Assert.assertEquals;

public class LruEvicterTest {
    @Test
    public void shouldEvictValue() {
        EvictStrategy<String, Integer> evicter = new LruEvicter<>();
        Cache<String, Integer> cache = new PlainCache<>(1, evicter);
        cache.put("1", 1);
        evicter.evict(cache);
        assertEquals(0, cache.size());
    }

    @Test
    public void shouldMoveToLatestEntryOnApplyEvictionPolicy() throws NoSuchFieldException, IllegalAccessException {
        EvictStrategy<String, Integer> evicter = new LruEvicter<>();
        Cache<String, Integer> cache = new PlainCache<>(2, evicter);
        String key = "2";
        Integer expectedValue = 1;
        cache.put(key, expectedValue);
        cache.put("1", 1);
        evicter.applyEvictionPolicy(cache, key);
        Map<String, Integer> map = getPrivateField("cache", PlainCache.class, cache);
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        iterator.next();
        Map.Entry<String, Integer> secondEntry = iterator.next();
        assertEquals(expectedValue, secondEntry.getValue());
    }
}
