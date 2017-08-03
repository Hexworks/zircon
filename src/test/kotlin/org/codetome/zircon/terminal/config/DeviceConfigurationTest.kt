package org.codetome.zircon.terminal.config

import org.codetome.zircon.ANSITextColor
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class DeviceConfigurationTest {

    @Test
    fun test() {
        DeviceConfiguration(
                blinkLengthInMilliSeconds = 5,
                cursorStyle = CursorStyle.UNDER_BAR,
                cursorColor = ANSITextColor.GREEN,
                isCursorBlinking = true,
                isClipboardAvailable = true)
    }


}