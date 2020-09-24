package com.epam.cache.evict;

import com.epam.cache.Cache;
import com.epam.cache.PlainCache;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RandomEvicterTest {
    @Test
    public void shouldEvictValue() {
        EvictStrategy<String, Integer> evicter = new RandomEvicter<>();
        Cache<String, Integer> cache = new PlainCache<>(1, evicter);
        cache.put("1", 1);
        evicter.evict(cache);
        assertEquals(0, cache.size());
    }
}
