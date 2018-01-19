package org.codetome.zircon.api.resource

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Symbols
import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.font.FontLoaderRegistry
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.internal.font.impl.VirtualFontLoader
import org.junit.Before
import org.junit.Test

class CP437TilesetResourceTest {

    lateinit var target: Font

    @Before
    fun setUp() {
        FontLoaderRegistry.setFontLoader(VirtualFontLoader())
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
        assertThat(CP437TilesetResource.convertCp437toUnicode(1).toInt())
                .isEqualTo(Symbols.FACE_WHITE.toInt())
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
