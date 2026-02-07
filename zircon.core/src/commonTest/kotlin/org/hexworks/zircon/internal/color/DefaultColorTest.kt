package org.hexworks.zircon.internal.color

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.color.Color
import kotlin.test.Test
import kotlin.test.assertFailsWith

class DefaultColorTest {

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = DefaultColor(RED, GREEN, BLUE, 123).cacheKey

        result shouldBe "TextColor(r=5,g=10,b=15,a=123)"
    }

    @Test
    fun shouldCreateDefaultTextColorWithProperDefaultAlpha() {
        val result = DefaultColor(RED, GREEN, BLUE)

        result.red shouldBe RED
        result.green shouldBe GREEN
        result.blue shouldBe BLUE
        result.alpha shouldBe Color.defaultAlpha()
    }

    @Test
    fun testTinting() {
        assertFailsWith<IllegalArgumentException> { DefaultColor(RED, GREEN, BLUE).tint(-0.3) }
        assertFailsWith<IllegalArgumentException> { DefaultColor(RED, GREEN, BLUE).tint(1.3) }

        val tintedBlack = Color.create(0, 0, 0).tint()
        tintedBlack.red shouldBe 178
        tintedBlack.green shouldBe 178
        tintedBlack.blue shouldBe 178

        val tintedWhite = Color.create(255, 255, 255).tint()
        tintedWhite.red shouldBe 255
        tintedWhite.green shouldBe 255
        tintedWhite.blue shouldBe 255

        val tintedRed = Color.create(255, 0, 0).tint()
        tintedRed.red shouldBe 255
        tintedRed.green shouldBe 178
        tintedRed.blue shouldBe 178

        val tinted = Color.create(125, 87, 200).tint()
        tinted.red shouldBe 216
        tinted.green shouldBe 204
        tinted.blue shouldBe 238
    }

    @Test
    fun testShading() {
        assertFailsWith<IllegalArgumentException> { DefaultColor(RED, GREEN, BLUE).shade(-0.3) }
        assertFailsWith<IllegalArgumentException> { DefaultColor(RED, GREEN, BLUE).shade(1.3) }

        val shadedBlack = Color.create(0, 0, 0).shade()
        shadedBlack.red shouldBe 0
        shadedBlack.green shouldBe 0
        shadedBlack.blue shouldBe 0

        val shadedWhite = Color.create(255, 255, 255).shade(1.0)
        shadedWhite.red shouldBe 0
        shadedWhite.green shouldBe 0
        shadedWhite.blue shouldBe 0

        val shadedColor = Color.create(100, 100, 100).shade(0.5)
        shadedColor.red shouldBe 50
        shadedColor.green shouldBe 50
        shadedColor.blue shouldBe 50
    }

    @Test
    fun testToning() {
        assertFailsWith<IllegalArgumentException> { DefaultColor(RED, GREEN, BLUE).tone(-0.3) }
        assertFailsWith<IllegalArgumentException> { DefaultColor(RED, GREEN, BLUE).tone(1.3) }

        val tonedBlack = Color.create(0, 0, 0).tone()
        tonedBlack.red shouldBe 0
        tonedBlack.green shouldBe 0
        tonedBlack.blue shouldBe 0

        val toneWhite = Color.create(255, 255, 255).tone(1.0)
        toneWhite.red shouldBe 255
        toneWhite.green shouldBe 255
        toneWhite.blue shouldBe 255

        val toneColor = Color.create(125, 100, 150).tone(0.5)
        toneColor.red shouldBe 125
        toneColor.green shouldBe 112
        toneColor.blue shouldBe 138
    }

    companion object {
        const val RED = 5
        const val GREEN = 10
        const val BLUE = 15
    }

}
