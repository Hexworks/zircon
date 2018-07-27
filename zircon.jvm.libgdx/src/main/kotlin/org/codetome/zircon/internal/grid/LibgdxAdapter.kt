package org.codetome.zircon.internal.grid

import com.badlogic.gdx.ApplicationAdapter
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.grid.DeviceConfiguration

class LibgdxAdapter(initialTileset: Tileset,
                    initialSize: Size,
                    private val deviceConfiguration: DeviceConfiguration,
                    private val libgdxTerminal: LibgdxTileGrid = LibgdxTileGrid(
                            initialTileset = initialTileset,
                            initialSize = initialSize,
                            deviceConfiguration = deviceConfiguration))
    : ApplicationAdapter(), InternalTileGrid by libgdxTerminal {

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
