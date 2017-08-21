package org.codetome.zircon.api

import org.codetome.zircon.graphics.layer.Layer
import org.codetome.zircon.util.rex.File
import org.codetome.zircon.util.rex.decompressGZIPByteArray
import java.io.InputStream

class REXPaintResource(rexFile: File) {
    private val rx = rexFile

    fun info() {
        println("File version: "+ this.rx.version)
        println("Number of layers: "+ this.rx.numOfLayers)
        for (layer in this.rx.layers) {
            println("Layer: ${layer.width}*${layer.height}, Cells: ${layer.cells.size}")
        }
    }

    fun toLayerList(): List<Layer> {
        val layers: MutableList<Layer> = mutableListOf()
        for (layer in this.rx.layers) {
            layers.add(layer.toDefaultLayer())
        }
        return layers.toList()
    }

    companion object {
        @JvmStatic
        fun loadREXFile(source: InputStream): REXPaintResource {
            val decompressed = decompressGZIPByteArray(source.readBytes())
            return REXPaintResource(File.fromByteArray(decompressed))
        }
    }
}
