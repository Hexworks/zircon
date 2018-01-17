package org.codetome.zircon.internal.terminal

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.terminal.config.DeviceConfiguration

interface TerminalComponentsLoader {

    fun fetchScreenSize(): ScreenSize

    fun buildTerminal(title: String,
                      size: Size,
                      deviceConfiguration: DeviceConfiguration,
                      font: Font,
                      fullScreen: Boolean): InternalTerminal

}
