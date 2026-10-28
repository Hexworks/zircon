package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Size
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.box
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.hexworks.zircon.api.graphics.extensions.fill
import kotlin.test.Test

class DefaultBoxTest {

    @Test
    fun test() {
        box {
            boxType = DOUBLE
            size = Size(5, 5)
        }.apply {
            fill(characterTile {
                character = 'x'
            })
        }.toString() shouldBe EXPECTED_BOX
    }

    companion object {
        val EXPECTED_BOX = """
╔═══╗
║xxx║
║xxx║
║xxx║
╚═══╝
""".trimMargin()
    }
}
