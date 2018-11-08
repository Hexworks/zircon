package org.hexworks.zircon.internal.util.rex

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.TilesetResource
import java.nio.ByteBuffer

/**
 * Represents a REX Paint Layer, which contains its size information (width, height) and a [List] of [REXCell]s.
 */
data class REXLayer(private val width: Int,
                    private val height: Int,
                    private val cells: List<REXCell>) {

    fun getWidth() = width

    fun getHeight() = height

    fun getCells() = cells

    /**
     * Returns itself as a [REXLayer].
     */
    fun toLayer(tileset: TilesetResource): Layer {
        val layer = LayerBuilder.newBuilder()
                .withTileset(tileset)
                .withSize(Size.create(width, height))
                .build()

        for (y in 0 until height) {
            for (x in 0 until width) {
                // Have to swap x and y due to how image data is stored
                val cell = cells[x * height + y]
                if (cell.getBackgroundColor() == TRANSPARENT_BACKGROUND) {
                    // Skip transparent characters
                    continue
                }
                layer.setTileAt(
                        Position.create(x, y),
                        TileBuilder.newBuilder()
                                .withCharacter(cell.getCharacter())
                                .withBackgroundColor(cell.getBackgroundColor())
                                .withForegroundColor(cell.getForegroundColor())
                                .build()
                )
            }
        }
        return layer
    }

    companion object {
        val TRANSPARENT_BACKGROUND = TileColor.create(255, 0, 255)

        /**
         * Factory method for [REXLayer], which reads out Layer information from a [ByteBuffer].
         * This automatically generates [REXCell] objects from the data provided.
         */
        fun fromByteBuffer(buffer: ByteBuffer): REXLayer {
            val width = buffer.int
            val height = buffer.int

            val cells: MutableList<REXCell> = mutableListOf()
            for (i in 0 until width * height) {
                cells.add(REXCell.fromByteBuffer(buffer))
            }

            return REXLayer(width, height, cells)
        }
    }
}
