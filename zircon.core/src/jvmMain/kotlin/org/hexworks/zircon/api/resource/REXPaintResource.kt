package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.util.rex.REXFile
import org.hexworks.zircon.internal.util.rex.decompressGZIPByteArray
import java.io.InputStream

/**
 * This class can be used to load REXPaint files.
 */
class REXPaintResource private constructor(private val rexFile: REXFile) {

    /**
     * Converts this [REXPaintResource] to a list of [Layer]s.
     */
    @JvmOverloads
    fun toLayerList(tileset: TilesetResource = RuntimeConfig.config.defaultTileset): List<Layer> {
        return this.rexFile.getLayers().map { it.toLayer(tileset) }.toList()
    }

    companion object {

        /**
         * Loads a REXPaint file from the given input stream.
         */
        @JvmStatic
        fun loadREXFile(source: InputStream): REXPaintResource {
            return REXPaintResource(REXFile.fromByteArray(decompressGZIPByteArray(source.readBytes())))
        }
    }
}
