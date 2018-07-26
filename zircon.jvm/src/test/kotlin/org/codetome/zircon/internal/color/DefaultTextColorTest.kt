package org.codetome.zircon.internal.color

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.color.TextColor
import org.junit.Test

class DefaultTextColorTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = DefaultTextColor(RED, GREEN, BLUE, 123).generateCacheKey()

        assertThat(result).isEqualTo("a:123r:5g:10b:15")
    }

    @Test
    fun shouldCreateDefaultTextColorWithProperDefaultAlpha() {
        val result = DefaultTextColor(RED, GREEN, BLUE)

        assertThat(result.getRed()).isEqualTo(RED)
        assertThat(result.getGreen()).isEqualTo(GREEN)
        assertThat(result.getBlue()).isEqualTo(BLUE)
        assertThat(result.getAlpha()).isEqualTo(TextColor.defaultAlpha())
    }

    companion object {
        const val RED = 5
        const val GREEN = 10
        const val BLUE = 15
    }

}
