package org.hexworks.zircon.internal.tileset.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.BuiltInCP437Tileset
import org.hexworks.zircon.internal.tileset.SwingTilesetLoader
import org.junit.Ignore
import org.junit.Test

@Ignore
class BufferedImageCP437TilesetTest {

    val target = SwingTilesetLoader().loadTilesetFrom(BuiltInCP437Tileset.WANDERLUST_16X16)

    @Test
    fun shouldProperlyCacheFontWhenFetchingRegionTwice() {
        val firstResult = target.fetchTextureForTile(Tile.defaultTile())
        val secondResult = target.fetchTextureForTile(Tile.defaultTile())

        assertThat(firstResult).isSameAs(secondResult)
    }

    @Test
    fun shouldNotBeTheSameWhenRegionIsLoadedForDifferentTextChars() {
        val firstResult = target.fetchTextureForTile(Tile.defaultTile())
        val secondResult = target.fetchTextureForTile(Tile.empty())

        assertThat(firstResult).isNotSameAs(secondResult)
    }


    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToFetchRegionWithChar() {
        target.fetchTextureForTile(Tile.defaultTile().withCharacter(1.toChar()))
    }

    @Test
    fun shouldProperlyReportSize() {
        val expectedSize = 16
        assertThat(target.width()).isEqualTo(expectedSize)
        assertThat(target.height()).isEqualTo(expectedSize)
    }
}
