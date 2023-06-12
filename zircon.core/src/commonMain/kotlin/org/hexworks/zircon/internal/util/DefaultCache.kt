package org.hexworks.zircon.internal.util

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.util.Cache
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@OptIn(kotlin.time.ExperimentalTime::class)
class DefaultCache<R : Cacheable> constructor(
    maximumSize: Long = 5000,
    duration: Duration = 1.minutes
) : Cache<R> {

    private val backend = io.github.reactivecircus.cache4k.Cache.Builder()
        .expireAfterAccess(duration)
        .maximumCacheSize(maximumSize)
        .build<String, R>()

    override fun retrieveIfPresentOrNull(key: String): R? {
        return backend.get(key)
    }

    override fun store(obj: R): R {
        backend.put(obj.cacheKey, obj)
        return obj
    }
}
