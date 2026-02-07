package org.hexworks.zircon.internal.component.impl

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultComponentStyleSetTest {

    lateinit var target: ComponentStyleSet

    @BeforeTest
    fun setUp() {
        target = componentStyleSet {
            activeStyle = ACTIVE_STYLE
            focusedStyle = FOCUSED_STYLE
            defaultStyle = DEFAULT_STYLE
            disabledStyle = DISABLED_STYLE
            highlightedStyle = HIGHLIGHTED_STYLE
        }
    }

    @Test
    fun shouldFailForMissingStyle() {
        shouldThrow<IllegalArgumentException> {
            DefaultComponentStyleSet(mapOf())
        }
    }

    @Test
    fun shouldProperlyUseDefaultForUnsetStyles() {
        val styles = componentStyleSet {
            activeStyle = ACTIVE_STYLE
            focusedStyle = FOCUSED_STYLE
            highlightedStyle = HIGHLIGHTED_STYLE
            defaultStyle = DEFAULT_STYLE
        }

        styles.fetchStyleFor(ComponentState.DISABLED) shouldBe DEFAULT_STYLE
    }

    @Test
    fun shouldProperlyReturnStyleByKey() {
        target.fetchStyleFor(ComponentState.DISABLED) shouldBe DISABLED_STYLE
    }


    companion object {
        val ACTIVE_STYLE = styleSet {
            foregroundColor = DefaultAnsiPalette[ANSIColor.RED]
            backgroundColor = DefaultAnsiPalette[ANSIColor.BLACK]
        }
        val FOCUSED_STYLE = styleSet {
            foregroundColor = DefaultAnsiPalette[ANSIColor.GREEN]
            backgroundColor = DefaultAnsiPalette[ANSIColor.WHITE]
        }
        val DEFAULT_STYLE = styleSet {
            foregroundColor = DefaultAnsiPalette[ANSIColor.YELLOW]
            backgroundColor = DefaultAnsiPalette[ANSIColor.BLUE]
        }
        val DISABLED_STYLE = styleSet {
            foregroundColor = DefaultAnsiPalette[ANSIColor.BLACK]
            backgroundColor = DefaultAnsiPalette[ANSIColor.MAGENTA]
        }
        val HIGHLIGHTED_STYLE = styleSet {
            foregroundColor = DefaultAnsiPalette[ANSIColor.MAGENTA]
            backgroundColor = DefaultAnsiPalette[ANSIColor.GREEN]
        }
    }

}
