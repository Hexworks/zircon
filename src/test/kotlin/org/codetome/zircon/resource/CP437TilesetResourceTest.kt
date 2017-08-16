package org.codetome.zircon.resource

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.CP437TilesetResource
import org.codetome.zircon.font.CharacterMetadata
import org.junit.Test

class CP437TilesetResourceTest {

    val target = CP437TilesetResource.WANDERLUST_16X16.asJava2DFont()

    @Test
    fun shouldBeAbleToLoadAllTilesets() {
        CP437TilesetResource.values().forEach {
            try {
                it.asJava2DFont()
            } catch (e: Exception) {
                throw IllegalStateException("Tileset resource '${it.path}' failed to load!", e)
            }
        }
    }

    @Test
    fun shouldProperlyLoadMetadataForChar() {
        val result = target.fetchMetadataForChar('a')

        assertThat(result).isEqualTo(listOf(CharacterMetadata(
                char = 'a',
                x = 1,
                y = 6)))
    }

    @Test
    fun shouldProperlyFetchWidthAndHeightForTileset() {
        val expectedSize = 16

        assertThat(target.getWidth()).isEqualTo(expectedSize)
        assertThat(target.getHeight()).isEqualTo(expectedSize)
    }

    @Test
    fun shouldProperlyFetchCP437IndexForChar() {
        val result = CP437TilesetResource.fetchCP437IndexForChar('x')

        assertThat(result).isEqualTo(X_CP437_INDEX)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToFetchCP437IndexForInvalidChar() {
        CP437TilesetResource.fetchCP437IndexForChar(1.toChar())
    }

    companion object {
        val X_CP437_INDEX = 120
    }
}