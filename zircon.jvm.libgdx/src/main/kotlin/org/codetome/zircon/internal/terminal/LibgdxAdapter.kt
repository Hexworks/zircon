package org.codetome.zircon.internal.terminal

import com.badlogic.gdx.ApplicationAdapter
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.terminal.config.DeviceConfiguration

class LibgdxAdapter(initialFont: Font,
                    initialSize: Size,
                    private val deviceConfiguration: DeviceConfiguration,
                    private val libgdxTerminal: LibgdxTerminal = LibgdxTerminal(
                            initialFont = initialFont,
                            initialSize = initialSize,
                            deviceConfiguration = deviceConfiguration))
    : ApplicationAdapter(), InternalTerminal by libgdxTerminal {

    override fun create() {
        libgdxTerminal.doCreate()
    }

    override fun render() {
        libgdxTerminal.doRender()
    }

    override fun dispose() {
        libgdxTerminal.doDispose()
    }
}
