package com.manastudent.core.config.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.benmanes.caffeine.cache.CacheWriter;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.manastudent.core.util.RedisUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CaffeineLevelCache extends AbstractStringFirstLevelCache {

    Log log = LogFactory.get();
    LoadingCache<String, String> caffeineCache;

    public CaffeineLevelCache() {
        // 查询二级缓存
        LoadingCache<String, String> cache = Caffeine.newBuilder()
                .recordStats()
                .initialCapacity(100)
                .maximumSize(1000)
                .writer(new CacheWriter<String, String>() {
                    @Override
                    public void write(String key, String value) {
                        log.info("caffeineCache write key=" + key + ", value=" + value);
                    }

                    @Override
                    public void delete(String key, String value, RemovalCause cause) {
                        log.info("caffeineCache delete key=" + key);
                    }
                })
                .expireAfterWrite(60, TimeUnit.SECONDS) //一个元素将会在其创建或者最近一次被更新之后的一段时间后被认定为过期项
                .build(key -> {
                    String value = RedisUtils.get(key);
                    if (StrUtil.isEmpty(value)) {
                        return null;
                    }
                    log.info("get key from caffeineCache-redis, key: " + key);
                    return value;
                });
        caffeineCache = cache;
    }

    @Override
    public String get(String key) {
        //查找缓存，如果缓存不存在则生成缓存元素
        String value = caffeineCache.get(key);
        log.info("get key from caffeineCache, key: " + key);
        return value;
    }

    @Override
    public void set(String key, String value) {
        //放入二级缓存
        RedisUtils.set(key, value);
        RedisUtils.expire(key, 5, TimeUnit.MINUTES);
    }

    @Override
    public void set(String key, String value, Long expertTime, TimeUnit timeUnit) {
        RedisUtils.set(key, value);
        RedisUtils.expire(key, expertTime, timeUnit);
    }

    @Override
    public void delete(String key) {
        caffeineCache.invalidate(key);
        RedisUtils.delete(key);
        log.info("delete key from caffeineCache, key: " + key);
    }
}
