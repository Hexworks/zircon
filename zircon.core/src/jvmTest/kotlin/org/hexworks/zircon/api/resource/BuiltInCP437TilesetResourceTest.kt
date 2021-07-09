package org.hexworks.zircon.api.resource

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.util.CP437Utils
import org.junit.Before
import org.junit.Test

class BuiltInCP437TilesetResourceTest {

    lateinit var target: TilesetResource

    @Before
    fun setUp() {
        target = BuiltInCP437TilesetResource.WANDERLUST_16X16
    }

    @Test
    fun shouldProperlyConvertCpToUnicode() {
        assertThat(CP437Utils.convertCp437toUnicode(1).toInt())
            .isEqualTo(Symbols.FACE_WHITE.toInt())
    }

    @Test
    fun shouldProperlyLoadMetadataForChar() {
//        val result = target.fetchMetadataForChar('a')
//
//        assertThat(result).isEqualTo(listOf(TileTextureMetadata.create(
//                char = 'a',
//                x = 1,
//                y = 6)))
    }

    @Test
    fun shouldProperlyFetchWidthAndHeightForTileset() {
        val expectedSize = 16

        assertThat(target.width).isEqualTo(expectedSize)
        assertThat(target.height).isEqualTo(expectedSize)
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
