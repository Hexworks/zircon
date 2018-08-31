package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.BoxBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.junit.Test

class DefaultBoxTest {

    @Test
    fun test() {
        assertThat(BoxBuilder.newBuilder()
                .boxType(BoxType.DOUBLE)
                .size(Size.create(5, 5))
                .style(StyleSetBuilder.newBuilder().build())
                .build()
                .fill(TileBuilder.newBuilder().character('x').build())
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
