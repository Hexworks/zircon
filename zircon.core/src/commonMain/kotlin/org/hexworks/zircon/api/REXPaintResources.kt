package org.hexworks.zircon.api

import korlibs.io.async.AsyncByteArrayDeque
import korlibs.io.file.std.openAsZip
import korlibs.memory.Buffer
import korlibs.memory.getInt32
import korlibs.memory.getInt8
import korlibs.io.compression.deflate.GZIPNoCrc
import korlibs.io.compression.uncompress
import korlibs.io.stream.AsyncOutputStream
import korlibs.io.stream.readAll
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.loadResource
import org.hexworks.zircon.internal.resource.REXPaintResource
import org.hexworks.zircon.internal.util.CP437Utils
import org.hexworks.zircon.internal.util.rex.REXCell
import org.hexworks.zircon.internal.util.rex.REXFile
import org.hexworks.zircon.internal.util.rex.REXLayer

/**
 * Loads a REXPaint file from the given path.
 */
suspend fun loadREXFile(path: String, resourceType: ResourceType = ResourceType.FILESYSTEM): REXPaintResource {
    val resource = loadResource(Resource.create(path, resourceType))

    val result = AsyncByteArrayDeque();
    GZIPNoCrc.uncompress(resource.openInputStream(), result)
    return loadREXFile(result.readAll())
}

/**
 * Loads a REXPaint file from the given [ByteArray].
 */
fun loadREXFile(source: ByteArray): REXPaintResource {
    return REXPaintResource(source.toREXFile())
}

/**
 * Factory method for [REXFile]. It takes an uncompressed [ByteArray] argument and reads out the REX Paint
 * [REXFile] object as defined in the <a href="http://www.gridsagegames.com/rexpaint/manual.txt">REX
 * Paint manual</a>.
 */
internal fun ByteArray.toREXFile(): REXFile {
    this.reverse() // because it is little endian
    val buffer = Buffer(this)
    val idx = 0.toProperty()

    val version = buffer.getS32LE(idx.getAndIncrement() * Int.SIZE_BYTES)
    val numberOfLayers = buffer.getS32LE(idx.getAndIncrement() * Int.SIZE_BYTES)

    val layers: MutableList<REXLayer> = mutableListOf()
    for (i in 0 until numberOfLayers) {
        layers.add(buffer.toRexLayer(idx))
    }

    return REXFile(version, numberOfLayers, layers)
}

/**
 * Factory method for [REXLayer], which reads out Layer information from a [Buffer].
 * This automatically generates [REXCell] objects from the data provided.
 */
internal fun Buffer.toRexLayer(idx: Property<Int>): REXLayer {
    val width = getS32LE(idx.getAndIncrement() * Int.SIZE_BYTES)
    val height = getS32LE(idx.getAndIncrement() * Int.SIZE_BYTES)

    val cells: MutableList<REXCell> = mutableListOf()
    for (i in 0 until width * height) {
        cells.add(toREXCell(idx))
    }

    return REXLayer(width, height, cells)
}

/**
 * Factory method for [REXCell], which reads out Cell information from a [Buffer].
 */
internal fun Buffer.toREXCell(idx: Property<Int>): REXCell {
    return REXCell(
        character = CP437Utils.convertCp437toUnicode(getS32LE(idx.getAndIncrement() * Int.SIZE_BYTES)),
        foregroundColor = TileColor.create(
            getS8(idx.getAndIncrement()).toInt() and 0xFF,
            getS8(idx.getAndIncrement()).toInt() and 0xFF,
            getS8(idx.getAndIncrement()).toInt() and 0xFF,
            255
        ),
        backgroundColor = TileColor.create(
            getS8(idx.getAndIncrement()).toInt() and 0xFF,
            getS8(idx.getAndIncrement()).toInt() and 0xFF,
            getS8(idx.getAndIncrement()).toInt() and 0xFF,
            255
        )
    )
}

fun Property<Int>.getAndIncrement() = value.also {
    value += 1
}
