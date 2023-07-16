package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.GraphicalTileBuilder
import org.hexworks.zircon.api.builder.graphics.BoxBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.junit.Test

class DefaultBoxTest {

    @Test
    fun test() {
        assertThat(BoxBuilder.newBuilder()
            .withBoxType(BoxType.DOUBLE)
            .withSize(Size.create(5, 5))
            .withStyle(StyleSetBuilder.newBuilder().build())
            .build()
            .apply {
                fill(GraphicalTileBuilder.newBuilder().withCharacter('x').build())
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
