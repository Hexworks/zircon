package org.codetome.zircon.graphics.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.ANSITextColor
import org.codetome.zircon.Modifier.BLINK
import org.codetome.zircon.Modifier.CROSSED_OUT
import org.codetome.zircon.graphics.style.DefaultStyleSet
import org.junit.Before
import org.junit.Test

class DefaultStyleSetTest {

    lateinit var target: DefaultStyleSet

    @Before
    fun setUp() {
        target = DefaultStyleSet()
    }

    @Test
    fun test() {
    }

    companion object {
        val EXPECTED_BG_COLOR = ANSITextColor.YELLOW
        val EXPECTED_FG_COLOR = ANSITextColor.CYAN
        val EXPECTED_MODIFIERS = setOf(CROSSED_OUT, BLINK)

        val OTHER_STYLE = DefaultStyleSet(
                foregroundColor = EXPECTED_FG_COLOR,
                backgroundColor = EXPECTED_BG_COLOR,
                modifiers = EXPECTED_MODIFIERS)
    }
}