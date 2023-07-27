package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.junit.Before
import org.junit.Test

class DefaultComponentStyleSetTest {

    lateinit var target: ComponentStyleSet

    @Before
    fun setUp() {
        target = componentStyleSet {
            activeStyle = ACTIVE_STYLE
            focusedStyle = FOCUSED_STYLE
            defaultStyle = DEFAULT_STYLE
            disabledStyle = DISABLED_STYLE
            highlightedStyle = HIGHLIGHTED_STYLE
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldFailForMissingStyle() {
        DefaultComponentStyleSet(mapOf())
    }

    @Test
    fun shouldProperlyUseDefaultForUnsetStyles() {
        val styles = componentStyleSet {
            activeStyle = ACTIVE_STYLE
            focusedStyle = FOCUSED_STYLE
            highlightedStyle = HIGHLIGHTED_STYLE
            defaultStyle = DEFAULT_STYLE
        }

        assertThat(styles.fetchStyleFor(ComponentState.DISABLED))
            .isEqualTo(DEFAULT_STYLE)
    }

    @Test
    fun shouldProperlyReturnStyleByKey() {
        assertThat(target.fetchStyleFor(ComponentState.DISABLED)).isEqualTo(DISABLED_STYLE)
    }


    companion object {
        val ACTIVE_STYLE = styleSet {
            foregroundColor = RED
            backgroundColor = BLACK
        }
        val FOCUSED_STYLE = styleSet {
            foregroundColor = GREEN
            backgroundColor = WHITE
        }
        val DEFAULT_STYLE = styleSet {
            foregroundColor = YELLOW
            backgroundColor = BLUE
        }
        val DISABLED_STYLE = styleSet {
            foregroundColor = BLACK
            backgroundColor = MAGENTA
        }
        val HIGHLIGHTED_STYLE = styleSet {
            foregroundColor = MAGENTA
            backgroundColor = GREEN
        }
    }

}
