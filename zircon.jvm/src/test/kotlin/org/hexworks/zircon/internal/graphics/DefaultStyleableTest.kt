package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.internal.behavior.impl.DefaultStyleable
import org.hexworks.zircon.api.Modifiers
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
        assertThat(target.activeModifiers()).isEmpty()
    }

    @Test
    fun shouldHaveProperFGByDefault() {
        assertThat(target.foregroundColor())
                .isEqualTo(TileColor.defaultForegroundColor())
    }

    @Test
    fun shouldHaveProperBGByDefault() {
        assertThat(target.backgroundColor())
                .isEqualTo(TileColor.defaultBackgroundColor())
    }

    @Test
    fun shouldProperlyEnableModifier() {
        val modifier = Modifiers.verticalFlip()

        target.enableModifiers(modifier)

        assertThat(target.activeModifiers()).containsExactly(modifier)
    }

    @Test
    fun shouldProperlyDisableModifier() {
        val modifier = Modifiers.verticalFlip()

        target.enableModifiers(modifier)
        target.disableModifiers(modifier)

        assertThat(target.activeModifiers()).isEmpty()
    }

    @Test
    fun shouldProperlyEnableModifiers() {
        target.enableModifiers(setOf(Modifiers.verticalFlip(), Modifiers.crossedOut()))

        assertThat(target.activeModifiers()).containsExactlyInAnyOrder(Modifiers.verticalFlip(), Modifiers.crossedOut())
    }

    @Test
    fun shouldProperlySetModifiers() {
        val modifiers = setOf(Modifiers.verticalFlip(), Modifiers.crossedOut())

        target.setModifiers(modifiers)

        assertThat(target.activeModifiers()).containsExactlyInAnyOrder(Modifiers.verticalFlip(), Modifiers.crossedOut())
    }

    @Test
    fun shouldProperlyClearModifiers() {
        target.enableModifiers(Modifiers.verticalFlip())

        target.clearModifiers()

        assertThat(target.activeModifiers())
                .isEmpty()
    }

    @Test
    fun twoIdenticalStyleSetsShouldBeEqual() {
        assertThat(StyleSet.defaultStyle()).isEqualTo(StyleSet.defaultStyle())
    }
}
