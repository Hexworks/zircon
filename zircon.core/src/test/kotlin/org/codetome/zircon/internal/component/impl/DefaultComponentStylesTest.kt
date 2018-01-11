package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.ComponentStyles
import org.junit.Before
import org.junit.Test

class DefaultComponentStylesTest {

    lateinit var target: ComponentStyles

    @Before
    fun setUp() {
        target = ComponentStylesBuilder.newBuilder()
                .activeStyle(ACTIVE_STYLE)
                .focusedStyle(FOCUSED_STYLE)
                .defaultStyle(DEFAULT_STYLE)
                .disabledStyle(DISABLED_STYLE)
                .mouseOverStyle(MOUSE_OVER_STYLE)
                .build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldFailForMissingStyle() {
        DefaultComponentStyles(mapOf())
    }

    @Test
    fun shouldProperlyReturnStyleByKey() {
        assertThat(target.getStyleFor(ComponentState.DISABLED)).isEqualTo(DISABLED_STYLE)
    }

    @Test
    fun shouldBeDefaultByDefault() {
        assertThat(target.getCurrentStyle()).isEqualTo(DEFAULT_STYLE)
    }

    @Test
    fun shouldProperlyGiveFocus() {
        assertThat(target.giveFocus()).isEqualTo(FOCUSED_STYLE)
        assertThat(target.getCurrentStyle()).isEqualTo(FOCUSED_STYLE)
    }

    @Test
    fun shouldProperlyActivate() {
        assertThat(target.activate()).isEqualTo(ACTIVE_STYLE)
        assertThat(target.getCurrentStyle()).isEqualTo(ACTIVE_STYLE)
    }

    @Test
    fun shouldProperlyDisable() {
        assertThat(target.disable()).isEqualTo(DISABLED_STYLE)
        assertThat(target.getCurrentStyle()).isEqualTo(DISABLED_STYLE)
    }

    @Test
    fun shouldProperlyMouseOver() {
        assertThat(target.mouseOver()).isEqualTo(MOUSE_OVER_STYLE)
        assertThat(target.getCurrentStyle()).isEqualTo(MOUSE_OVER_STYLE)
    }

    @Test
    fun shouldProperlyReset() {
        target.mouseOver()
        assertThat(target.reset()).isEqualTo(DEFAULT_STYLE)
        assertThat(target.getCurrentStyle()).isEqualTo(DEFAULT_STYLE)
    }

    companion object {
        val ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(ANSITextColor.RED)
                .backgroundColor(ANSITextColor.BLACK)
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(ANSITextColor.GREEN)
                .backgroundColor(ANSITextColor.WHITE)
                .build()
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(ANSITextColor.YELLOW)
                .backgroundColor(ANSITextColor.BLUE)
                .build()
        val DISABLED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(ANSITextColor.BLACK)
                .backgroundColor(ANSITextColor.MAGENTA)
                .build()
        val MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(ANSITextColor.MAGENTA)
                .backgroundColor(ANSITextColor.GREEN)
                .build()
    }

}