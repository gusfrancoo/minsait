package com.minsait.user_api.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheLRUDemo {

    public static class CacheLRU<K, V> {
        private final int capacity;
        private final LinkedHashMap<K, V> cache;

        public CacheLRU(int capacity) {
            this.capacity = capacity;
            this.cache = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                    return size() > CacheLRU.this.capacity;
                }
            };
        }

        public void put(K key, V value) {
            cache.put(key, value);
        }

        public V get(K key) {
            return cache.getOrDefault(key, null);
        }

        public void remove(K key) {
            cache.remove(key);
        }

        public int size() {
            return cache.size();
        }

        @Override
        public String toString() {
            return cache.toString();
        }
    }

    public static void main(String[] args) {
        CacheLRU<Integer, String> cache = new CacheLRU<>(3);

        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");

        System.out.println("Cache inicial: " + cache);

        cache.get(1);

        cache.put(4, "D");

        System.out.println("Após inserção do 4: " + cache);

        cache.remove(3);
        System.out.println("Após remoção do 3: " + cache);

        System.out.println("Tamanho atual: " + cache.size());
    }
}
