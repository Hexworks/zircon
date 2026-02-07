package org.hexworks.zircon.api.resource

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.util.CP437Index
import org.hexworks.zircon.internal.util.convertCp437toUnicode
import kotlin.test.BeforeTest
import kotlin.test.Test

class BuiltInCP437TilesetResourceTest {

    lateinit var target: TilesetResource

    @BeforeTest
    fun setUp() {
        target = BuiltInCP437TilesetResource.WANDERLUST_16X16
    }

    @Test
    fun shouldProperlyConvertCpToUnicode() {
        1.convertCp437toUnicode().code shouldBe Symbols.FACE_WHITE.code
    }

    @Test
    fun shouldProperlyLoadMetadataForChar() {
//        val result = target.fetchMetadataForChar('a')
//
//        result shouldBe listOf(TileTextureMetadata.create(
//                char = 'a',
//                x = 1,
//                y = 6))
    }

    @Test
    fun shouldProperlyFetchWidthAndHeightForTileset() {
        val expectedSize = 16

        target.width shouldBe expectedSize
        target.height shouldBe expectedSize
    }

    @Test
    fun shouldProperlyFetchCP437IndexForChar() {
        val result = 'x'.CP437Index()

        result shouldBe X_CP437_INDEX
    }

    @Test
    fun shouldNotBeAbleToFetchCP437IndexForInvalidChar() {
        shouldThrow<IllegalArgumentException> {
            1.toChar().CP437Index()
        }
    }

    companion object {
        const val X_CP437_INDEX = 120
    }
}
