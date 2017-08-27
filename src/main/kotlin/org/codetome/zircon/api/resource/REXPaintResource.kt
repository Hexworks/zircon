package org.codetome.zircon.api.resource

import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.util.rex.REXFile
import org.codetome.zircon.internal.util.rex.decompressGZIPByteArray
import java.io.InputStream

class REXPaintResource(rexFile: REXFile) {
    private val rex = rexFile

    fun toLayerList(): List<Layer> {
        return this.rex.getLayers().map { it.toLayer() }.toList()
    }

    companion object {
        @JvmStatic
        fun loadREXFile(source: InputStream): REXPaintResource {
            return REXPaintResource(REXFile.fromByteArray(decompressGZIPByteArray(source.readBytes())))
        }
    }
}
