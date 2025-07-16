package com.minsait.user_api;
import com.minsait.user_api.utils.CacheLRUDemo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CacheLRUTest {

    private CacheLRUDemo.CacheLRU<Integer, String> cache;

    @BeforeEach
    public void setUp() {
        cache = new CacheLRUDemo.CacheLRU<>(3);
    }

    @Test
    public void putAndGet() {
        cache.put(1, "A");
        cache.put(2, "B");

        assertEquals("A", cache.get(1));
        assertEquals("B", cache.get(2));
    }

    @Test
    public void evictLRU() {
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.get(1); // Usa o item 1
        cache.put(4, "D"); // Remove o 2

        assertNull(cache.get(2));
        assertEquals("A", cache.get(1));
        assertEquals("C", cache.get(3));
        assertEquals("D", cache.get(4));
    }

    @Test
    public void removeItem() {
        cache.put(1, "A");
        cache.remove(1);

        assertNull(cache.get(1));
    }

    @Test
    public void checkSize() {
        cache.put(1, "A");
        cache.put(2, "B");

        assertEquals(2, cache.size());

        cache.put(3, "C");
        cache.put(4, "D");

        assertEquals(3, cache.size());
    }
}

