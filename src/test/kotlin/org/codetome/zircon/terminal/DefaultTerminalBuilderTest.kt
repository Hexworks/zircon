package org.codetome.zircon.terminal

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.DeviceConfigurationBuilder
import org.codetome.zircon.api.TerminalBuilder
import org.codetome.zircon.api.CP437TilesetResource
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.internal.util.reflection.Whitebox

class DefaultTerminalBuilderTest {

    lateinit var target: TerminalBuilder

    @Before
    fun setUp() {
        target = TerminalBuilder()
    }

    // headless exception needs to be fixed
    @Ignore
    @Test
    fun shouldSetFieldsProperly() {
        val autoOpen = true
        val size = Size(5, 4)
        val title = "Title"
        val deviceConfiguration = DeviceConfigurationBuilder.newBuilder()
                .blinkLengthInMilliSeconds(5)
                .build()
        val font = CP437TilesetResource.WANDERLUST_16X16.asJava2DFont()

        target.autoOpenTerminalFrame(autoOpen)
                .initialTerminalSize(size)
                .title(title)
                .deviceConfiguration(deviceConfiguration)
                .font(font)

        assertThat(Whitebox.getInternalState(target, "autoOpenTerminalFrame"))
                .isEqualTo(autoOpen)
        assertThat(Whitebox.getInternalState(target, "title"))
                .isEqualTo(title)
        assertThat(Whitebox.getInternalState(target, "deviceConfiguration"))
                .isEqualTo(deviceConfiguration)
        assertThat(Whitebox.getInternalState(target, "target"))
                .isEqualTo(font)
        assertThat(Whitebox.getInternalState(target, "initialTerminalSize"))
                .isEqualTo(size)

    }
}