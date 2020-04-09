package org.hexworks.zircon.internal.color

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.color.TileColor
import org.junit.Test
import kotlin.test.assertFailsWith

class DefaultTileColorTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = DefaultTileColor(RED, GREEN, BLUE, 123).cacheKey

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

    @Test
    fun testTinting() {
        assertFailsWith<IllegalArgumentException> { DefaultTileColor(RED, GREEN, BLUE).tint(-0.3) }
        assertFailsWith<IllegalArgumentException> { DefaultTileColor(RED, GREEN, BLUE).tint(1.3) }

        val tintedBlack = TileColor.create(0, 0, 0).tint()
        assertThat(tintedBlack.red).isEqualTo(178)
        assertThat(tintedBlack.green).isEqualTo(178)
        assertThat(tintedBlack.blue).isEqualTo(178)

        val tintedWhite = TileColor.create(255, 255, 255).tint()
        assertThat(tintedWhite.red).isEqualTo(255)
        assertThat(tintedWhite.green).isEqualTo(255)
        assertThat(tintedWhite.blue).isEqualTo(255)

        val tintedRed = TileColor.create(255, 0, 0).tint()
        assertThat(tintedRed.red).isEqualTo(255)
        assertThat(tintedRed.green).isEqualTo(178)
        assertThat(tintedRed.blue).isEqualTo(178)

        val tinted = TileColor.create(125, 87, 200).tint()
        assertThat(tinted.red).isEqualTo(216)
        assertThat(tinted.green).isEqualTo(204)
        assertThat(tinted.blue).isEqualTo(238)
    }

    @Test
    fun testShading() {
        assertFailsWith<IllegalArgumentException> { DefaultTileColor(RED, GREEN, BLUE).shade(-0.3) }
        assertFailsWith<IllegalArgumentException> { DefaultTileColor(RED, GREEN, BLUE).shade(1.3) }

        val shadedBlack = TileColor.create(0, 0, 0).shade()
        assertThat(shadedBlack.red).isEqualTo(0)
        assertThat(shadedBlack.green).isEqualTo(0)
        assertThat(shadedBlack.blue).isEqualTo(0)

        val shadedWhite = TileColor.create(255, 255, 255).shade(1.0)
        assertThat(shadedWhite.red).isEqualTo(0)
        assertThat(shadedWhite.green).isEqualTo(0)
        assertThat(shadedWhite.blue).isEqualTo(0)

        val shadedColor = TileColor.create(100, 100, 100).shade(0.5)
        assertThat(shadedColor.red).isEqualTo(50)
        assertThat(shadedColor.green).isEqualTo(50)
        assertThat(shadedColor.blue).isEqualTo(50)
    }

    @Test
    fun testToning() {
        assertFailsWith<IllegalArgumentException> { DefaultTileColor(RED, GREEN, BLUE).tone(-0.3) }
        assertFailsWith<IllegalArgumentException> { DefaultTileColor(RED, GREEN, BLUE).tone(1.3) }

        val tonedBlack = TileColor.create(0, 0, 0).tone()
        assertThat(tonedBlack.red).isEqualTo(0)
        assertThat(tonedBlack.green).isEqualTo(0)
        assertThat(tonedBlack.blue).isEqualTo(0)

        val toneWhite = TileColor.create(255, 255, 255).tone(1.0)
        assertThat(toneWhite.red).isEqualTo(255)
        assertThat(toneWhite.green).isEqualTo(255)
        assertThat(toneWhite.blue).isEqualTo(255)

        val toneColor = TileColor.create(125, 100, 150).tone(0.5)
        assertThat(toneColor.red).isEqualTo(125)
        assertThat(toneColor.green).isEqualTo(112)
        assertThat(toneColor.blue).isEqualTo(138)
    }

    companion object {
        const val RED = 5
        const val GREEN = 10
        const val BLUE = 15
    }

}
