package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.junit.Before
import org.junit.Test

class DefaultComponentStyleSetTest {

    lateinit var target: ComponentStyleSet

    @Before
    fun setUp() {
        target = ComponentStyleSetBuilder.newBuilder()
                .withActiveStyle(ACTIVE_STYLE)
                .withFocusedStyle(FOCUSED_STYLE)
                .withDefaultStyle(DEFAULT_STYLE)
                .withDisabledStyle(DISABLED_STYLE)
                .withMouseOverStyle(MOUSE_OVER_STYLE)
                .build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldFailForMissingStyle() {
        DefaultComponentStyleSet(mapOf())
    }

    @Test
    fun shouldProperlyUseDefaultForUnsetStyles() {
        val styles = ComponentStyleSetBuilder.newBuilder()
                .withActiveStyle(ACTIVE_STYLE)
                .withFocusedStyle(FOCUSED_STYLE)
                .withDefaultStyle(DEFAULT_STYLE)
                .withMouseOverStyle(MOUSE_OVER_STYLE)
                .build()

        assertThat(styles.fetchStyleFor(ComponentState.DISABLED))
                .isEqualTo(DEFAULT_STYLE)
    }

    @Test
    fun shouldProperlyReturnStyleByKey() {
        assertThat(target.fetchStyleFor(ComponentState.DISABLED)).isEqualTo(DISABLED_STYLE)
    }


    companion object {
        val ACTIVE_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(ANSITileColor.RED)
                .withBackgroundColor(ANSITileColor.BLACK)
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(ANSITileColor.GREEN)
                .withBackgroundColor(ANSITileColor.WHITE)
                .build()
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(ANSITileColor.YELLOW)
                .withBackgroundColor(ANSITileColor.BLUE)
                .build()
        val DISABLED_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(ANSITileColor.BLACK)
                .withBackgroundColor(ANSITileColor.MAGENTA)
                .build()
        val MOUSE_OVER_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(ANSITileColor.MAGENTA)
                .withBackgroundColor(ANSITileColor.GREEN)
                .build()
    }

}
