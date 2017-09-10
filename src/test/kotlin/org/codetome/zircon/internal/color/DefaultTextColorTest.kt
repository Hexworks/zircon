package org.codetome.zircon.internal.color

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.factory.TextColorFactory
import org.junit.Test

class DefaultTextColorTest {

    @Test
    fun shouldCreateDefaultTextColorWithProperDefaultAlpha() {
        val result = DefaultTextColor(RED, GREEN, BLUE)

        assertThat(result.getRed()).isEqualTo(RED)
        assertThat(result.getGreen()).isEqualTo(GREEN)
        assertThat(result.getBlue()).isEqualTo(BLUE)
        assertThat(result.getAlpha()).isEqualTo(TextColorFactory.DEFAULT_ALPHA)
    }

    companion object {
        val RED = 5
        val GREEN = 10
        val BLUE = 15
    }

}