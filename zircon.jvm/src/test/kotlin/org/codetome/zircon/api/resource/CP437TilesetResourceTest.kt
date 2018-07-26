package org.codetome.zircon.api.resource

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.graphics.Symbols
import org.codetome.zircon.api.font.TextureRegionMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.font.impl.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.TestFontLoader
import org.codetome.zircon.internal.util.CP437Utils
import org.junit.Before
import org.junit.Test

class CP437TilesetResourceTest {

    lateinit var target: Font

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(TestFontLoader())
        target = CP437TilesetResource.WANDERLUST_16X16.toFont()
    }

    @Test
    fun shouldBeAbleToLoadAllTilesets() {
        CP437TilesetResource.values().forEach {
            try {
                it.toFont()
            } catch (e: Exception) {
                throw IllegalStateException("Tileset resource '${it.path}' failed to load!", e)
            }
        }
    }

    @Test
    fun shouldProperlyConvertCpToUnicode() {
        assertThat(CP437Utils.convertCp437toUnicode(1).toInt())
                .isEqualTo(Symbols.FACE_WHITE.toInt())
    }

    @Test
    fun shouldProperlyLoadMetadataForChar() {
        val result = target.fetchMetadataForChar('a')

        assertThat(result).isEqualTo(listOf(TextureRegionMetadata.create(
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
        val result = CP437Utils.fetchCP437IndexForChar('x')

        assertThat(result).isEqualTo(X_CP437_INDEX)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToFetchCP437IndexForInvalidChar() {
        CP437Utils.fetchCP437IndexForChar(1.toChar())
    }

    companion object {
        val X_CP437_INDEX = 120
    }
}
