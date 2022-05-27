package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.internal.resource.REXPaintResource
import org.hexworks.zircon.internal.util.CP437Utils
import org.hexworks.zircon.internal.util.rex.REXCell
import org.hexworks.zircon.internal.util.rex.REXFile
import org.hexworks.zircon.internal.util.rex.REXLayer
import org.hexworks.zircon.internal.util.rex.decompressGZIPByteArray
import java.io.File
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * JVM utilities for loading [REXPaintResource]s from files **on the JVM**.
 */
object REXPaintResources {

    /**
     * Loads a REXPaint file from the given path.
     */
    @JvmStatic
    fun loadREXFile(path: String): REXPaintResource {
        return loadREXFile(File(path).inputStream())
    }

    /**
     * Loads a REXPaint file from the given [InputStream].
     */
    @JvmStatic
    fun loadREXFile(source: InputStream): REXPaintResource {
        return REXPaintResource(decompressGZIPByteArray(source.readBytes()).toREXFile())
    }

    /**
     * Factory method for [REXFile]. It takes an uncompressed [ByteArray] argument and reads out the REX Paint
     * [REXFile] object as defined in the <a href="http://www.gridsagegames.com/rexpaint/manual.txt">REX
     * Paint manual</a>.
     */
    internal fun ByteArray.toREXFile(): REXFile {
        val buffer = ByteBuffer.wrap(this)
        buffer.order(ByteOrder.LITTLE_ENDIAN)

        val version = buffer.int
        val numberOfLayers = buffer.int

        val layers: MutableList<REXLayer> = mutableListOf()
        for (i in 0 until numberOfLayers) {
            layers.add(buffer.toRexLayer())
        }

        return REXFile(version, numberOfLayers, layers)
    }

    /**
     * Factory method for [REXLayer], which reads out Layer information from a [ByteBuffer].
     * This automatically generates [REXCell] objects from the data provided.
     */
    internal fun ByteBuffer.toRexLayer(): REXLayer {
        val width = int
        val height = int

        val cells: MutableList<REXCell> = mutableListOf()
        for (i in 0 until width * height) {
            cells.add(toREXCell())
        }

        return REXLayer(width, height, cells)
    }

    /**
     * Factory method for [REXCell], which reads out Cell information from a [ByteBuffer].
     */
    internal fun ByteBuffer.toREXCell(): REXCell {
        return REXCell(
            character = CP437Utils.convertCp437toUnicode(int),
            foregroundColor = TileColor.create(
                get().toInt() and 0xFF,
                get().toInt() and 0xFF,
                get().toInt() and 0xFF,
                255
            ),
            backgroundColor = TileColor.create(
                get().toInt() and 0xFF,
                get().toInt() and 0xFF,
                get().toInt() and 0xFF,
                255
            )
        )
    }
}
