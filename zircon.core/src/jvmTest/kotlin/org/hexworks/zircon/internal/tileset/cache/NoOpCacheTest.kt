package org.hexworks.zircon.internal.tileset.cache

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.util.impl.NoOpCache
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

        val result0 = target.retrieveIfPresentOrNull("${tc.character}")
        val result1 = target.retrieveIfPresentOrNull("${tc.character}")

        assertThat(result0 != null).isFalse()
        assertThat(result1 != null).isFalse()
    }

    @Test
    fun shouldReturnEmptyForCharIfStoredThenRetrieved() {
        val tc = Tile.defaultTile()

        target.store(tc)
        val result = target.retrieveIfPresentOrNull("${tc.character}")

        assertThat(result).isNull()
    }
}
