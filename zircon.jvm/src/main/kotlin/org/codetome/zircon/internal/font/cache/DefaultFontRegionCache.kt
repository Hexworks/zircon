package org.codetome.zircon.internal.font.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.font.FontRegionCache
import java.util.*
import java.util.concurrent.TimeUnit

class DefaultFontRegionCache<R>(maximumSize: Long = 5000,
                                duration: Long = 1,
                                timeUnit: TimeUnit = TimeUnit.MINUTES)
    : FontRegionCache<R> {

    // TODO: test me

    private val cachedImages = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(maximumSize)
            .expireAfterAccess(duration, timeUnit)
            .build<String, R>()

    override fun retrieveIfPresent(textCharacter: TextCharacter): Optional<R> {
        return Optional.ofNullable(cachedImages.getIfPresent(textCharacter.generateCacheKey()))
    }

    override fun store(textCharacter: TextCharacter, region: R) {
        cachedImages.put(textCharacter.generateCacheKey(), region)
    }
}
