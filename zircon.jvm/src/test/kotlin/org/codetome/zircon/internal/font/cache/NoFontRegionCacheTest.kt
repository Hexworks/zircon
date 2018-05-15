package org.codetome.zircon.internal.font.cache

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.junit.Before
import org.junit.Test

class NoFontRegionCacheTest {

    lateinit var target: NoFontRegionCache<String>

    @Before
    fun setUp() {
        target = NoFontRegionCache()
    }

    @Test
    fun shouldReturnEmptyForCharIfCalledTwice() {
        val tc = TextCharacterBuilder.defaultCharacter()

        val result0 = target.retrieveIfPresent(tc)
        val result1 = target.retrieveIfPresent(tc)

        assertThat(result0).isNotPresent
        assertThat(result1).isNotPresent
    }

    @Test
    fun shouldReturnEmptyForCharIfStoredThenRetrieved() {
        val tc = TextCharacterBuilder.defaultCharacter()

        target.store(tc, tc.getCharacter().toString())
        val result = target.retrieveIfPresent(tc)

        assertThat(result).isNotPresent
    }
}
