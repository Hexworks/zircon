package org.codetome.zircon.internal.terminal

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.terminal.config.DeviceConfiguration
import java.awt.Toolkit

class Java2DTerminalComponentsLoader : TerminalComponentsLoader {

    override fun fetchScreenSize(): ScreenSize {
        return Toolkit.getDefaultToolkit().screenSize.let {
            ScreenSize(it.width, it.height)
        }
    }

    override fun buildTerminal(title: String,
                               size: Size,
                               deviceConfiguration: DeviceConfiguration,
                               font: Font,
                               fullScreen: Boolean): InternalTerminal {
        return SwingTerminalFrame(
                title = title,
                size = size,
                deviceConfiguration = deviceConfiguration,
                fullScreen = fullScreen,
                font = font).apply {
            isVisible = true
        }
    }

}
