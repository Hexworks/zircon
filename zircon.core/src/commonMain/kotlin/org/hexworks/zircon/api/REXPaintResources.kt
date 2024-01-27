package org.hexworks.zircon.api

import korlibs.io.compression.deflate.GZIP
import korlibs.io.compression.uncompress
import korlibs.io.stream.FastByteArrayInputStream
import korlibs.io.stream.openFastStream
import korlibs.memory.getInt8
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.loadResource
import org.hexworks.zircon.internal.resource.REXPaintResource
import org.hexworks.zircon.internal.util.CP437Utils
import org.hexworks.zircon.internal.util.rex.REXCell
import org.hexworks.zircon.internal.util.rex.REXFile
import org.hexworks.zircon.internal.util.rex.REXLayer

// -----------------------------------------------------------------
// Appendix B: .xp Format Specification (and Import Libraries)
// -----------------------------------------------------------------
//
// If you are making a console/grid-based game with ASCII graphics, such as a traditional roguelike, by importing .xp files directly you will save both development time
// and huge amounts of space due to the highly compressed format (even smaller than PNGs of the same image/dimensions). This is the original intent behind REXPaint's design:
// a tool for drawing lots of ASCII art or maps for use in roguelikes.
//
// The .xp files are deflated with zlib (specifically they are gzipped files created via gzofstream); once decompressed the format is binary, and data is stored in the following
// order (bit size in parenthesis, 32 referring to integers and 8 to unsigned chars):
//
//  #-----xp format version (32)
// A-----number of layers (32)
//  /----image width (32)
//  |    image height (32)
//  |  /-ASCII code (32) (little-endian!)
// B|  | foreground color red (8)
//  |  | foreground color green (8)
//  |  | foreground color blue (8)
//  | C| background color red (8)
//  |  | background color green (8)
//  \--\-background color blue (8)

// The file begins with the internal .xp format version number, You can ignore this value, as it is only used by REXPaint itself (in case future changes to the format are required).
// Note that because pre-R9 versions of REXPaint did not include an .xp version number, it's actually stored as a negative value to differentiate old image files
// (which begin with a positive layer count) in order to detect and automatically update them.

// The actual image data begins with A, the number of image layers (always a number from 1 to 9 in the current version), followed by a section B for each layer
// (the image width/height is stored for every layer due to the method of serialization used, but will always be the same values for every layer).

// Section C is then repeated for each cell in the image, storing the image's 2D matrix in 1D array format. I.e., the length of that matrix would equal width*height,
// and if you need them you can get the x-value with "index/height", and y-value with "index%height" (my own matrix class stores its data in a 1D array so I just read all the cells straight in).
// Note that this means the image data happens to be stored in column-major order!

// One thing to remember about directly reading .xp files is that undrawn (empty) cells on any layer (even the base layer) are considered transparent,
// which is identified by the background color 255,0,255.
// Thus to rebuild the image as seen in REXPaint, always first test a cell's background color and skip drawing those cells which are transparent, while converting any visible
// (i.e., non-covered) transparent cells on the lowest layer to black.

// If you don't like working with binary, an alternative is to export images to one of the available text-based formats: TXT, CSV, or XML (see Appendix E).

/**
 * Loads a REXPaint file from the given path.
 */
suspend fun loadREXFile(path: String, resourceType: ResourceType = ResourceType.FILESYSTEM): REXPaintResource {
    return loadREXFile(loadResource(Resource.create(path, resourceType)).readBytes().uncompress(GZIP))
}

/**
 * Loads a REXPaint file from the given [ByteArray].
 */
suspend fun loadREXFile(source: ByteArray): REXPaintResource {
    return REXPaintResource(source.toREXFile())
}

/**
 * Factory method for [REXFile]. It takes an uncompressed [ByteArray] argument and reads out the REX Paint
 * [REXFile] object as defined in the <a href="http://www.gridsagegames.com/rexpaint/manual.txt">REX
 * Paint manual</a>.
 */
internal fun ByteArray.toREXFile(): REXFile {
    val stream = this.openFastStream();

    val version = stream.readS32LE()
    val numberOfLayers = stream.readS32LE()

    return REXFile(
        version = version,
        numberOfLayers = numberOfLayers,
        layers = 0.until(numberOfLayers).map {
            stream.nextRexLayer()
        }
    )
}

internal fun FastByteArrayInputStream.nextRexLayer(): REXLayer {
    val width = readS32LE()
    val height = readS32LE()
    return REXLayer(
        width = width,
        height = height,
        cells = 0.until(width*height).map {
            nextRexCell()
        }
    )
}

internal fun FastByteArrayInputStream.nextRexCell(): REXCell {
    return REXCell(
        character = CP437Utils.convertCp437toUnicode(readS32LE()),
        foregroundColor = TileColor.create(
            readS8() and 0xFF,
            readS8() and 0xFF,
            readS8() and 0xFF,
            255
        ),
        backgroundColor = TileColor.create(
            readS8() and 0xFF,
            readS8() and 0xFF,
            readS8() and 0xFF,
            255
        )
    )
}