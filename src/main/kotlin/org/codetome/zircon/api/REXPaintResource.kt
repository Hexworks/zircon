package org.codetome.zircon.api

import org.codetome.zircon.graphics.layer.Layer
import org.codetome.zircon.util.rex.File
import org.codetome.zircon.util.rex.decompressGZIPByteArray
import java.io.InputStream

class REXPaintResource(rexFile: File) {
    private val rx = rexFile

    fun toLayerList(): List<Layer> {
        return this.rx.getLayers().map { it.toZirconLayer() }.toList()
    }

    companion object {
        @JvmStatic
        fun loadREXFile(source: InputStream): REXPaintResource {
            return REXPaintResource(File.fromByteArray(decompressGZIPByteArray(source.readBytes())))
        }
    }
}
