package org.codetome.zircon.api.builder


import org.assertj.core.api.Assertions
import org.codetome.zircon.api.builder.grid.DeviceConfigurationBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.grid.CursorStyle
import org.junit.Before
import org.junit.Test
import org.mockito.internal.util.reflection.Whitebox

class DeviceConfigurationBuilderTest {

    lateinit var target: DeviceConfigurationBuilder

    @Before
    fun setUp() {
        target = DeviceConfigurationBuilder()
    }

    @Test
    fun shouldSetFieldsProperly() {
        val blinkLengthInMilliSeconds = 5L
        val clipboardAvailable = true
        val cursorBlinking = true
        val cursorColor = TextColor.fromString("#aabbcc")
        val cursorStyle = CursorStyle.UNDER_BAR

        target
                .blinkLengthInMilliSeconds(blinkLengthInMilliSeconds)
                .clipboardAvailable(clipboardAvailable)
                .cursorBlinking(cursorBlinking)
                .cursorColor(cursorColor)
                .cursorStyle(cursorStyle)

        Assertions.assertThat(Whitebox.getInternalState(target, "blinkLengthInMilliSeconds"))
                .isEqualTo(blinkLengthInMilliSeconds)
        Assertions.assertThat(Whitebox.getInternalState(target, "clipboardAvailable"))
                .isEqualTo(clipboardAvailable)
        Assertions.assertThat(Whitebox.getInternalState(target, "cursorBlinking"))
                .isEqualTo(cursorBlinking)
        Assertions.assertThat(Whitebox.getInternalState(target, "cursorColor"))
                .isEqualTo(cursorColor)
        Assertions.assertThat(Whitebox.getInternalState(target, "cursorStyle"))
                .isEqualTo(cursorStyle)

    }


}
