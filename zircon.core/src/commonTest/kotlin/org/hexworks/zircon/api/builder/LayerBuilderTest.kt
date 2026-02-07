package org.hexworks.zircon.api.builder

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import kotlin.test.BeforeTest
import kotlin.test.Test

class LayerBuilderTest {

    lateinit var target: LayerBuilder

    @BeforeTest
    fun setUp() {
        target = LayerBuilder()
    }

    @Test
    fun shouldProperlyBuildLayer() {
        val result = target.apply {
            size = SIZE
            offset = OFFSET
        }.build().apply {
            fill(FILLER)
        }

        result.size shouldBe SIZE

        result.position shouldBe OFFSET

        result.getTileAtOrNull(OFFSET) shouldBe FILLER
    }

    companion object {
        val SIZE = Size.create(4, 5)
        val FILLER = Tile.defaultTile().withCharacter('x')
        val OFFSET = Position.create(3, 4)
    }
}
