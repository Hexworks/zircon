package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.util.rex.REXFile
import kotlin.jvm.JvmOverloads

/**
 * This class can be used to load Zircon [Layer]s from [REXFile]s.
 */
class REXPaintResource(private val rexFile: REXFile) {

    /**
     * Converts this [REXPaintResource] to a list of [Layer]s.
     */
    @JvmOverloads
    fun toLayerList(tileset: TilesetResource = RuntimeConfig.config.defaultTileset): List<Layer> {
        return this.rexFile.layers.map { it.toLayer(tileset) }.toList()
    }
}
