package org.hexworks.zircon.api.resource

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.util.CP437Index
import org.hexworks.zircon.internal.util.convertCp437toUnicode
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
        assertThat(1.convertCp437toUnicode().code)
            .isEqualTo(Symbols.FACE_WHITE.code)
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
        val result = 'x'.CP437Index()

        assertThat(result).isEqualTo(X_CP437_INDEX)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldNotBeAbleToFetchCP437IndexForInvalidChar() {
        1.toChar().CP437Index()
    }

    companion object {
        const val X_CP437_INDEX = 120
    }
}
