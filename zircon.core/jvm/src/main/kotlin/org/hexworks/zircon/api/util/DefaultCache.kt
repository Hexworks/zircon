package org.hexworks.zircon.api.util

import com.github.benmanes.caffeine.cache.Caffeine
import org.hexworks.zircon.api.behavior.Cacheable
import java.util.concurrent.TimeUnit

class DefaultCache<R : Cacheable>(maximumSize: Long = 5000,
                                  duration: Long = 1,
                                  timeUnit: TimeUnit = TimeUnit.MINUTES) : Cache<R> {

    private val backend = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(maximumSize)
            .expireAfterAccess(duration, timeUnit)
            .build<String, R>()

    override fun retrieveIfPresent(key: String): Maybe<R> {
        return Maybe.ofNullable(backend.getIfPresent(key))
    }

    override fun store(key: String, obj: R): R {
        backend.put(key, obj)
        return obj
    }
}
