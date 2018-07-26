package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.api.modifier.BorderBuilder
import org.codetome.zircon.api.modifier.BorderPosition
import org.codetome.zircon.api.modifier.SimpleModifiers.*
import org.junit.Test

class DefaultStyleSetTest {

    @Test
    fun shouldBuildProperCacheKey() {
        val result = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.WHITE)
                .foregroundColor(TextColor.fromString("#aabbcc"))
                .modifiers(Modifiers.crossedOut(), BorderBuilder.newBuilder().borderPositions(BorderPosition.TOP).build())
                .build().generateCacheKey()
        assertThat(result).isEqualTo("fg:a:255r:170g:187b:204bg:a:255r:170g:170b:170mod:Border:SOLID:TOPSimpleModifiers:CrossedOut")
    }

    @Test
    fun shouldAddModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(Bold)

        val result = styleSet.withAddedModifiers(Italic)

        assertThat(StyleSet.defaultStyle().getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(Bold)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(Bold, Italic)
    }

    @Test
    fun shouldRemoveModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(Bold, Italic)

        val result = styleSet.withRemovedModifiers(Italic)

        assertThat(StyleSet.defaultStyle().getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(Bold, Italic)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(Bold)
    }

    @Test
    fun shouldSetModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(Bold, Italic)

        val result = styleSet.withModifiers(Italic, CrossedOut)

        assertThat(StyleSet.defaultStyle().getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(Bold, Italic)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(CrossedOut, Italic)
    }

    @Test
    fun shouldNotHaveModifiersByDefault() {
        assertThat(StyleSet.defaultStyle().getModifiers()).isEmpty()
    }

    @Test
    fun shouldGenerateEqualDefaults() {
        assertThat(StyleSet.defaultStyle()).isEqualTo(StyleSet.defaultStyle())
    }

}
