package org.codetome.zircon.util.rex

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextColorFactory
import org.codetome.zircon.graphics.layer.DefaultLayer
import org.codetome.zircon.graphics.layer.Layer as ZirconLayer
import java.awt.Color
import java.nio.ByteBuffer

/**
 * Represents a REX Paint Layer, which contains its size information (width, height) and a [List] of [Cell]s.
 */
data class Layer(private val width: Int,
                 private val height: Int,
                 private val cells: List<Cell>) {

    fun getWidth() = width

    fun getHeight() = height

    fun getCells() = cells

    /**
     * Returns itself as a [Layer].
     */
    fun toZirconLayer(): ZirconLayer {
        val layer = DefaultLayer(
                Size(width, height),
                TextCharacterBuilder.newBuilder()
                        .backgroundColor(TextColorFactory.fromRGB(0, 0, 0, 0))
                        .character(' ')
                        .build(),
                Position.of(0, 0)
        )
        for (y in 0 until height) {
            for (x in 0 until width) {
                // Have to swap x and y due to how image data is stored
                val cell = cells[x*height+y]
                if (cell.getBackgroundColor() == TRANSPARENT_BACKGROUND) {
                    // Skip transparent characters
                    continue
                }
                layer.setCharacterAt(
                        Position.of(x, y),
                        TextCharacterBuilder.newBuilder()
                                .character(cell.getCharacter())
                                .backgroundColor(TextColorFactory.fromAWTColor(cell.getBackgroundColor()))
                                .foregroundColor(TextColorFactory.fromAWTColor(cell.getForegroundColor()))
                                .build()
                )
            }
        }
        return layer
    }

    companion object {
        val TRANSPARENT_BACKGROUND = Color(255, 0, 255)

        /**
         * Factory method for [Layer], which reads out Layer information from a [ByteBuffer].
         * This automatically generates [Cell] objects from the data provided.
         */
        fun fromByteBuffer(buffer: ByteBuffer): Layer {
            val width = buffer.int
            val height = buffer.int

            val cells: MutableList<Cell> = mutableListOf()
            for (i in 0 until width*height) {
                cells.add(Cell.fromByteBuffer(buffer))
            }

            return Layer(width, height, cells)
        }
    }
}
