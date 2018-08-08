package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.internal.behavior.impl.DefaultStyleable
import org.codetome.zircon.api.interop.Modifiers
import org.junit.Before
import org.junit.Test

class DefaultStyleableTest {

    lateinit var target: DefaultStyleable

    @Before
    fun setUp() {
        target = DefaultStyleable(StyleSet.defaultStyle())
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
        val modifier = Modifiers.verticalFlip()

        target.enableModifiers(modifier)

        assertThat(target.getActiveModifiers()).containsExactly(modifier)
    }

    @Test
    fun shouldProperlyDisableModifier() {
        val modifier = Modifiers.verticalFlip()

        target.enableModifiers(modifier)
        target.disableModifiers(modifier)

        assertThat(target.getActiveModifiers()).isEmpty()
    }

    @Test
    fun shouldProperlyEnableModifiers() {
        target.enableModifiers(setOf(Modifiers.verticalFlip(), Modifiers.crossedOut()))

        assertThat(target.getActiveModifiers()).containsExactlyInAnyOrder(Modifiers.verticalFlip(), Modifiers.crossedOut())
    }

    @Test
    fun shouldProperlySetModifiers() {
        val modifiers = setOf(Modifiers.verticalFlip(), Modifiers.crossedOut())

        target.setModifiers(modifiers)

        assertThat(target.getActiveModifiers()).containsExactlyInAnyOrder(Modifiers.verticalFlip(), Modifiers.crossedOut())
    }

    @Test
    fun shouldProperlyClearModifiers() {
        target.enableModifiers(Modifiers.verticalFlip())

        target.clearModifiers()

        assertThat(target.getActiveModifiers())
                .isEmpty()
    }

    @Test
    fun twoIdenticalStyleSetsShouldBeEqual() {
        assertThat(StyleSet.defaultStyle()).isEqualTo(StyleSet.defaultStyle())
    }
}
