package org.hexworks.zircon.internal.color

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.color.TileColor
import org.junit.Test

class DefaultTileColorTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = DefaultTileColor(RED, GREEN, BLUE, 123).generateCacheKey()

        assertThat(result).isEqualTo("TextColor(r=5,g=10,b=15,a=123)")
    }

    @Test
    fun shouldCreateDefaultTextColorWithProperDefaultAlpha() {
        val result = DefaultTileColor(RED, GREEN, BLUE)

        assertThat(result.red).isEqualTo(RED)
        assertThat(result.green).isEqualTo(GREEN)
        assertThat(result.blue).isEqualTo(BLUE)
        assertThat(result.alpha).isEqualTo(TileColor.defaultAlpha())
    }

    companion object {
        const val RED = 5
        const val GREEN = 10
        const val BLUE = 15
    }

}
