package org.codetome.zircon.internal.tileset.cache

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.internal.util.impl.NoOpCache
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

        val result0 = target.retrieveIfPresent("${tc.character}")
        val result1 = target.retrieveIfPresent("${tc.character}")

        assertThat(result0.isPresent).isFalse()
        assertThat(result1.isPresent).isFalse()
    }

    @Test
    fun shouldReturnEmptyForCharIfStoredThenRetrieved() {
        val tc = Tile.defaultTile()

        target.store(tc.character + "", tc)
        val result = target.retrieveIfPresent("${tc.character}")

        assertThat(result.isPresent).isFalse()
    }
}
