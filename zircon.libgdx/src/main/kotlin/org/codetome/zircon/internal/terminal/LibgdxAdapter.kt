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
        println("Created")
        libgdxTerminal.createBatch()
    }

    override fun render() {
        libgdxTerminal.render()
    }

    override fun dispose() {
        println("disposed")
        libgdxTerminal.disposeBatch()
    }
}
