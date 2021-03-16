package com.manastudent.core.config.cache;

import java.util.concurrent.TimeUnit;

public abstract class AbstractStringFirstLevelCache extends AbstractFirstLevelCache<String, String> {
    public abstract void set(String key, String value, Long expertTime, TimeUnit timeUnit);
}
