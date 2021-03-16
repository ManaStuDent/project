package com.manastudent.core.config.cache;

public interface FirstLevelCache<K, V> {
    V get(K key);

    void set(K key, V value);

    void delete(K key);
}
