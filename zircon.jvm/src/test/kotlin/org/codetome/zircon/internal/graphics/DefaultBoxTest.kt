package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.graphics.builder.BoxBuilder
import org.codetome.zircon.api.graphics.builder.StyleSetBuilder
import org.codetome.zircon.api.graphics.BoxType
import org.junit.Test

class DefaultBoxTest {

    @Test
    fun test() {
        assertThat(BoxBuilder.newBuilder()
                .boxType(BoxType.DOUBLE)
                .filler('x')
                .size(Size.create(5, 5))
                .style(StyleSetBuilder.newBuilder().build())
                .build().toString())
                .isEqualTo(EXPECTED_BOX)
    }

    companion object {
        val EXPECTED_BOX = "╔═══╗\n" +
                "║xxx║\n" +
                "║xxx║\n" +
                "║xxx║\n" +
                "╚═══╝\n"
    }
}
