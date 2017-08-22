package org.codetome.zircon.internal.font.cache

import com.github.benmanes.caffeine.cache.Caffeine
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.font.FontRegionCache
import org.codetome.zircon.internal.font.ImageCachingStrategy
import java.awt.image.BufferedImage
import java.util.*
import java.util.concurrent.TimeUnit

class DefaultFontRegionCache<R>(private val imageCachingStrategy: ImageCachingStrategy,
                                maximumSize: Long = 5000,
                                duration: Long = 1,
                                timeUnit: TimeUnit = TimeUnit.MINUTES)
    : FontRegionCache<R> {

    private val cachedImages = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(maximumSize)
            .expireAfterAccess(duration, timeUnit)
            .build<Int, R>()

    override fun retrieveIfPresent(textCharacter: TextCharacter): Optional<R> {
        return Optional.ofNullable(cachedImages.getIfPresent(imageCachingStrategy.generateCacheKeyFor(textCharacter)))
    }

    override fun store(textCharacter: TextCharacter, region: R) {
        cachedImages.put(imageCachingStrategy.generateCacheKeyFor(textCharacter), region)
    }
}