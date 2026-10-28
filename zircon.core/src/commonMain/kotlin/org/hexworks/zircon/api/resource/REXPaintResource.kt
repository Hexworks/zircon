package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.util.rex.REXFile

/**
 * This class can be used to load Zircon [Layer]s from [REXFile]s.
 */
public class REXPaintResource internal constructor(private val rexFile: REXFile) {

    /**
     * Converts this [REXPaintResource] to a list of [Layer]s.
     */
    public fun toLayerList(tileset: TilesetResource = RuntimeConfig.config.defaultTileset): List<Layer> {
        return this.rexFile.layers.map { it.toLayer(tileset) }.toList()
    }
}
