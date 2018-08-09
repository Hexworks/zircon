package org.hexworks.zircon.internal.color

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.color.TileColor
import org.junit.Test

class DefaultTextColorTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = DefaultTextColor(RED, GREEN, BLUE, 123).generateCacheKey()

        assertThat(result).isEqualTo("TextColor(r=5,g=10,b=15,a=123)")
    }

    @Test
    fun shouldCreateDefaultTextColorWithProperDefaultAlpha() {
        val result = DefaultTextColor(RED, GREEN, BLUE)

        assertThat(result.getRed()).isEqualTo(RED)
        assertThat(result.getGreen()).isEqualTo(GREEN)
        assertThat(result.getBlue()).isEqualTo(BLUE)
        assertThat(result.getAlpha()).isEqualTo(TileColor.defaultAlpha())
    }

    companion object {
        const val RED = 5
        const val GREEN = 10
        const val BLUE = 15
    }

}
