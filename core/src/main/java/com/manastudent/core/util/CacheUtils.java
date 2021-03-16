package com.manastudent.core.util;

import com.manastudent.core.config.cache.AbstractStringFirstLevelCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtils {

    static AbstractStringFirstLevelCache cache;

    @Autowired
    public void setCache(AbstractStringFirstLevelCache caffeineCache) {
        CacheUtils.cache = caffeineCache;
    }

    /**
     * 查找缓存，如果缓存不存在则生成缓存元素
     *
     * @return value or null
     */
    public static String get(String key) {
        return cache.get(key);
    }

    /**
     * 设置缓存，默认 15min
     */
    public static void set(String key, String value) {
        cache.set(key, value);
    }

    /**
     * 设置缓存，提供时间和时间单位
     */
    public static void set(String key, String value, Long expertTime, TimeUnit timeUnit) {
        cache.set(key, value, expertTime, timeUnit);
    }

    /**
     * 删除缓存
     */
    public static void delete(String key) {
        cache.delete(key);
    }

}
