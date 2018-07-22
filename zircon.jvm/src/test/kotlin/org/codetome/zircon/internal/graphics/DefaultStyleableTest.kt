package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.builder.StyleSetBuilder
import org.codetome.zircon.api.interop.Modifiers
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable
import org.junit.Before
import org.junit.Test

class DefaultStyleableTest {

    lateinit var target: DefaultStyleable

    @Before
    fun setUp() {
        target = DefaultStyleable(StyleSetBuilder.defaultStyle())
    }

    @Test
    fun shouldHaveNoModifiersByDefault() {
        assertThat(target.getActiveModifiers()).isEmpty()
    }

    @Test
    fun shouldHaveProperFGByDefault() {
        assertThat(target.getForegroundColor())
                .isEqualTo(TextColor.defaultForegroundColor())
    }

    @Test
    fun shouldHaveProperBGByDefault() {
        assertThat(target.getBackgroundColor())
                .isEqualTo(TextColor.defaultBackgroundColor())
    }

    @Test
    fun shouldProperlyEnableModifier() {
        val modifier = Modifiers.BOLD

        target.enableModifiers(modifier)

        assertThat(target.getActiveModifiers()).containsExactly(modifier)
    }

    @Test
    fun shouldProperlyDisableModifier() {
        val modifier = Modifiers.BOLD

        target.enableModifiers(modifier)
        target.disableModifiers(modifier)

        assertThat(target.getActiveModifiers()).isEmpty()
    }

    @Test
    fun shouldProperlyEnableModifiers() {
        target.enableModifiers(setOf(Modifiers.BOLD, Modifiers.CROSSED_OUT))

        assertThat(target.getActiveModifiers()).containsExactlyInAnyOrder(Modifiers.BOLD, Modifiers.CROSSED_OUT)
    }

    @Test
    fun shouldProperlySetModifiers() {
        val modifiers = setOf(Modifiers.BOLD, Modifiers.CROSSED_OUT)

        target.setModifiers(modifiers)

        assertThat(target.getActiveModifiers()).containsExactlyInAnyOrder(Modifiers.BOLD, Modifiers.CROSSED_OUT)
    }

    @Test
    fun shouldProperlyClearModifiers() {
        target.enableModifiers(Modifiers.BOLD)

        target.clearModifiers()

        assertThat(target.getActiveModifiers())
                .isEmpty()
    }

    @Test
    fun twoIdenticalStyleSetsShouldBeEqual() {
        assertThat(StyleSetBuilder.defaultStyle()).isEqualTo(StyleSetBuilder.defaultStyle())
    }
}
