package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.internal.BuiltInModifiers.*
import org.junit.Test

class DefaultStyleSetTest {

    @Test
    fun shouldAddModifiersProperly() {
        val styleSet = StyleSetBuilder.DEFAULT_STYLE.withAddedModifiers(Bold)

        val result = styleSet.withAddedModifiers(Italic)

        assertThat(StyleSetBuilder.DEFAULT_STYLE.getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(Bold)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(Bold, Italic)
    }

    @Test
    fun shouldRemoveModifiersProperly() {
        val styleSet = StyleSetBuilder.DEFAULT_STYLE.withAddedModifiers(Bold, Italic)

        val result = styleSet.withRemovedModifiers(Italic)

        assertThat(StyleSetBuilder.DEFAULT_STYLE.getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(Bold, Italic)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(Bold)
    }

    @Test
    fun shouldSetModifiersProperly() {
        val styleSet = StyleSetBuilder.DEFAULT_STYLE.withAddedModifiers(Bold, Italic)

        val result = styleSet.withModifiers(Italic, CrossedOut)

        assertThat(StyleSetBuilder.DEFAULT_STYLE.getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(Bold, Italic)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(CrossedOut, Italic)
    }

    @Test
    fun shouldNotHaveModifiersByDefault() {
        assertThat(StyleSetBuilder.DEFAULT_STYLE.getModifiers()).isEmpty()
    }

}