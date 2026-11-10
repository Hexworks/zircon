package org.hexworks.zircon.api.graphics.extensions

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.graphics.BoxType.SINGLE
import kotlin.test.Test

class DrawBoxTest {

    @Test
    fun drawsBoxOverWholeSurfaceByDefault() {
        tileGraphics {
            size = Size(5, 5)
        }.apply {
            drawBox(DOUBLE)
            fill(characterTile {
                character = 'x'
            })
        }.toString() shouldBe EXPECTED_BOX
    }

    @Test
    fun drawsBoxAtPositionWithSize() {
        tileGraphics {
            size = Size(6, 5)
        }.apply {
            drawBox(boxType = SINGLE, position = Position(1, 1), size = Size(4, 3))
            fill(characterTile {
                character = '.'
            })
        }.toString() shouldBe EXPECTED_OFFSET_BOX
    }

    companion object {
        val EXPECTED_BOX = """
╔═══╗
║xxx║
║xxx║
║xxx║
╚═══╝
""".trimMargin()

        val EXPECTED_OFFSET_BOX = """
......
.┌──┐.
.│..│.
.└──┘.
......
""".trimMargin()
    }
}
