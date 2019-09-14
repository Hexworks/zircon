package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.internal.behavior.impl.DefaultStyleable
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
        assertThat(target.modifiers).isEmpty()
    }

    @Test
    fun shouldHaveProperFGByDefault() {
        assertThat(target.foregroundColor)
                .isEqualTo(TileColor.defaultForegroundColor())
    }

    @Test
    fun shouldHaveProperBGByDefault() {
        assertThat(target.backgroundColor)
                .isEqualTo(TileColor.defaultBackgroundColor())
    }

    @Test
    fun shouldProperlyEnableModifier() {
        val modifier = Modifiers.verticalFlip()

        target.enableModifiers(modifier)

        assertThat(target.modifiers).containsExactly(modifier)
    }

    @Test
    fun shouldProperlyDisableModifier() {
        val modifier = Modifiers.verticalFlip()

        target.enableModifiers(modifier)
        target.disableModifiers(modifier)

        assertThat(target.modifiers).isEmpty()
    }

    @Test
    fun shouldProperlyEnableModifiers() {
        target.enableModifiers(setOf(Modifiers.verticalFlip(), Modifiers.crossedOut()))

        assertThat(target.modifiers).containsExactlyInAnyOrder(Modifiers.verticalFlip(), Modifiers.crossedOut())
    }

    @Test
    fun shouldProperlySetModifiers() {
        val modifiers = setOf(Modifiers.verticalFlip(), Modifiers.crossedOut())

        target.modifiers = modifiers

        assertThat(target.modifiers).containsExactlyInAnyOrder(Modifiers.verticalFlip(), Modifiers.crossedOut())
    }

    @Test
    fun shouldProperlyClearModifiers() {
        target.enableModifiers(Modifiers.verticalFlip())

        target.clearModifiers()

        assertThat(target.modifiers)
                .isEmpty()
    }

    @Test
    fun twoIdenticalStyleSetsShouldBeEqual() {
        assertThat(StyleSet.defaultStyle()).isEqualTo(StyleSet.defaultStyle())
    }
}
