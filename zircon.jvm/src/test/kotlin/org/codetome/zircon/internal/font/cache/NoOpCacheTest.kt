package org.codetome.zircon.internal.font.cache

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.junit.Before
import org.junit.Test

class NoOpCacheTest {

    lateinit var target: NoOpCache<TextCharacter>

    @Before
    fun setUp() {
        target = NoOpCache()
    }

    @Test
    fun shouldReturnEmptyForCharIfCalledTwice() {
        val tc = TextCharacter.defaultCharacter()

        val result0 = target.retrieveIfPresent(tc.generateCacheKey())
        val result1 = target.retrieveIfPresent(tc.generateCacheKey())

        assertThat(result0.isPresent).isFalse()
        assertThat(result1.isPresent).isFalse()
    }

    @Test
    fun shouldReturnEmptyForCharIfStoredThenRetrieved() {
        val tc = TextCharacter.defaultCharacter()

        target.store(tc)
        val result = target.retrieveIfPresent(tc.generateCacheKey())

        assertThat(result.isPresent).isFalse()
    }
}
