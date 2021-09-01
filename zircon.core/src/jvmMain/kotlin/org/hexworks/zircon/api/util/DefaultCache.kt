package org.hexworks.zircon.api.util

import com.github.benmanes.caffeine.cache.Caffeine

import org.hexworks.zircon.api.behavior.Cacheable
import java.util.concurrent.TimeUnit

class DefaultCache<R : Cacheable>(
    maximumSize: Long = 5000,
    duration: Long = 1,
    timeUnit: TimeUnit = TimeUnit.MINUTES
) : Cache<R> {

    private val backend = Caffeine.newBuilder()
        .initialCapacity(100)
        .maximumSize(maximumSize)
        .expireAfterAccess(duration, timeUnit)
        .build<String, R>()

    override fun retrieveIfPresentOrNull(key: String): R? {
        return backend.getIfPresent(key)
    }

    override fun store(obj: R): R {
        backend.put(obj.cacheKey, obj)
        return obj
    }
}
