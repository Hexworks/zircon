package org.codetome.zircon.internal.color

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.platform.factory.TextColorFactory
import org.junit.Test

class DefaultTextColorTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = DefaultTextColor(RED, GREEN, BLUE, 123).generateCacheKey()

        assertThat(result).isEqualTo("51015123")
    }

    @Test
    fun shouldCreateDefaultTextColorWithProperDefaultAlpha() {
        val result = DefaultTextColor(RED, GREEN, BLUE)

        assertThat(result.getRed()).isEqualTo(RED)
        assertThat(result.getGreen()).isEqualTo(GREEN)
        assertThat(result.getBlue()).isEqualTo(BLUE)
        assertThat(result.getAlpha()).isEqualTo(TextColorFactory.defaultAlpha())
    }

    companion object {
        val RED = 5
        val GREEN = 10
        val BLUE = 15
    }

}
