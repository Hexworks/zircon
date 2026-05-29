package org.hexworks.zircon.internal.modifier

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.modifier.SimpleModifiers
import kotlin.test.Test

class SimpleModifiersTest {

    @Test
    fun shouldReturnProperCacheKeyForBlink() {
        SimpleModifiers.Blink.cacheKey shouldBe "Modifier.Blink"
    }

    @Test
    fun shouldReturnProperCacheKeyForHidden() {
        SimpleModifiers.Hidden.cacheKey shouldBe "Modifier.Hidden"
    }
}
