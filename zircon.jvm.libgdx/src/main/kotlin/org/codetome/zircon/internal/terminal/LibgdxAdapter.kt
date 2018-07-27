package org.codetome.zircon.internal.terminal

import com.badlogic.gdx.ApplicationAdapter
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.terminal.DeviceConfiguration

class LibgdxAdapter(initialTileset: Tileset,
                    initialSize: Size,
                    private val deviceConfiguration: DeviceConfiguration,
                    private val libgdxTerminal: LibgdxTerminal = LibgdxTerminal(
                            initialTileset = initialTileset,
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
