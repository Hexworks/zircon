package org.hexworks.zircon.internal.graphics

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.SimpleModifiers.Blink
import org.hexworks.zircon.api.modifier.SimpleModifiers.Hidden
import kotlin.test.Test

class DefaultStyleSetTest {

    @Test
    fun shouldBuildProperCacheKey() {
        val result = styleSet {
            backgroundColor = DefaultAnsiPalette[ANSIColor.WHITE]
            foregroundColor = Color.fromString("#aabbcc")
            modifiers = setOf(Blink)
        }.cacheKey
        result shouldStartWith "StyleSet(fg=TextColor(r=170,g=187,b=204,a=255)"
        result shouldContain "bg=TextColor("
        result shouldContain "m=[Modifier.Blink])"
    }

    @Test
    fun shouldAddModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withModifiers(Blink)

        val result = styleSet.withModifiers(Blink, Hidden)

        StyleSet.defaultStyle().modifiers.shouldBeEmpty()
        styleSet.modifiers shouldContainExactlyInAnyOrder listOf(Blink)
        result.modifiers shouldContainExactlyInAnyOrder listOf(Blink, Hidden)
    }

    @Test
    fun shouldSetModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withModifiers(Blink, Hidden)

        val result = styleSet.withModifiers(Hidden, Blink)

        StyleSet.defaultStyle().modifiers.shouldBeEmpty()
        styleSet.modifiers shouldContainExactlyInAnyOrder listOf(Blink, Hidden)
        result.modifiers shouldContainExactlyInAnyOrder listOf(Blink, Hidden)
    }

    @Test
    fun shouldNotHaveModifiersByDefault() {
        StyleSet.defaultStyle().modifiers.shouldBeEmpty()
    }

    @Test
    fun shouldGenerateEqualDefaults() {
        StyleSet.defaultStyle() shouldBe StyleSet.defaultStyle()
    }

}
