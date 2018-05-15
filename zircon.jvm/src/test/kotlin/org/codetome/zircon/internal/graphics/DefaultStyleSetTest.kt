package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.modifier.BorderBuilder
import org.codetome.zircon.api.modifier.BorderPosition
import org.codetome.zircon.internal.SimpleModifiers.*
import org.junit.Test

class DefaultStyleSetTest {

    @Test
    fun shouldBuildProperCacheKey() {
        val result = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.WHITE)
                .foregroundColor(TextColorFactory.fromString("#aabbcc"))
                .modifiers(Modifiers.CROSSED_OUT, BorderBuilder.newBuilder().borderPositions(BorderPosition.TOP).build())
                .build().generateCacheKey()
        assertThat(result).isEqualTo("170187204255WHITECrossedOutBorderSOLIDTOP")
    }

    @Test
    fun shouldAddModifiersProperly() {
        val styleSet = StyleSetBuilder.defaultStyle().withAddedModifiers(Bold)

        val result = styleSet.withAddedModifiers(Italic)

        assertThat(StyleSetBuilder.defaultStyle().getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(Bold)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(Bold, Italic)
    }

    @Test
    fun shouldRemoveModifiersProperly() {
        val styleSet = StyleSetBuilder.defaultStyle().withAddedModifiers(Bold, Italic)

        val result = styleSet.withRemovedModifiers(Italic)

        assertThat(StyleSetBuilder.defaultStyle().getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(Bold, Italic)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(Bold)
    }

    @Test
    fun shouldSetModifiersProperly() {
        val styleSet = StyleSetBuilder.defaultStyle().withAddedModifiers(Bold, Italic)

        val result = styleSet.withModifiers(Italic, CrossedOut)

        assertThat(StyleSetBuilder.defaultStyle().getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(Bold, Italic)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(CrossedOut, Italic)
    }

    @Test
    fun shouldNotHaveModifiersByDefault() {
        assertThat(StyleSetBuilder.defaultStyle().getModifiers()).isEmpty()
    }

}
