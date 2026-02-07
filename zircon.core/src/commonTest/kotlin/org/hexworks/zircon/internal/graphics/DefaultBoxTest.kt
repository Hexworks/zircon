package org.hexworks.zircon.internal.graphics

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.box
import org.hexworks.zircon.api.builder.graphics.withSize
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import kotlin.test.Test

class DefaultBoxTest {

    @Test
    fun test() {
        box {
            boxType = DOUBLE
            withSize {
                width = 5
                height = 5
            }
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
