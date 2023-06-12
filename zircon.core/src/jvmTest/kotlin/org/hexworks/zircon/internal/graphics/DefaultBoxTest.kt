package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.box
import org.hexworks.zircon.api.builder.graphics.withSize
import org.hexworks.zircon.api.graphics.BoxType.DOUBLE
import org.junit.Test

class DefaultBoxTest {

    @Test
    fun test() {
        assertThat(
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
            }
                .toString())
            .isEqualTo(EXPECTED_BOX)
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
