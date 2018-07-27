package org.codetome.zircon.internal.tileset.cache

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Tile
import org.junit.Before
import org.junit.Test

class NoOpCacheTest {

    lateinit var target: NoOpCache<Tile>

    @Before
    fun setUp() {
        target = NoOpCache()
    }

    @Test
    fun shouldReturnEmptyForCharIfCalledTwice() {
        val tc = Tile.defaultTile()

        val result0 = target.retrieveIfPresent("${tc.getCharacter()}")
        val result1 = target.retrieveIfPresent("${tc.getCharacter()}")

        assertThat(result0.isPresent).isFalse()
        assertThat(result1.isPresent).isFalse()
    }

    @Test
    fun shouldReturnEmptyForCharIfStoredThenRetrieved() {
        val tc = Tile.defaultTile()

        target.store(tc.getCharacter() + "", tc)
        val result = target.retrieveIfPresent("${tc.getCharacter()}")

        assertThat(result.isPresent).isFalse()
    }
}
