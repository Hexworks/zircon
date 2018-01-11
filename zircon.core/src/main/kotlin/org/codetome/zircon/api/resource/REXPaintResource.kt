package org.codetome.zircon.api.resource

import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.util.rex.REXFile
import org.codetome.zircon.internal.util.rex.decompressGZIPByteArray
import java.io.InputStream

/**
 * This class can be used to load REXPaint files.
 */
class REXPaintResource private constructor(rexFile: REXFile) {
    private val rexFile = rexFile

    /**
     * Converts this [REXPaintResource] to a list of [Layer]s.
     */
    fun toLayerList(): List<Layer> {
        return this.rexFile.getLayers().map { it.toLayer() }.toList()
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
