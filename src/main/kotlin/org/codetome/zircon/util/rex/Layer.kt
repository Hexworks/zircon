package org.codetome.zircon.util.rex

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.api.TextColorFactory
import org.codetome.zircon.graphics.layer.DefaultLayer
import java.awt.Color
import java.nio.ByteBuffer

data class Layer(val width: Int, val height: Int, val cells: List<Cell>) {

    fun toDefaultLayer(): DefaultLayer {
        val layer = DefaultLayer(
                Size(width, height),
                TextCharacterBuilder.newBuilder()
                        .backgroundColor(TextColorFactory.fromRGB(0, 0, 0, 0))
                        .character(' ')
                        .build(),
                Position.of(0, 0)
                )
        for (row in 0..height-1) {
            for (column in 0..width-1) {
                val cell = cells[row*width+column]
                if (cell.backgroundColor == TRANSPARENT_BACKGROUND) {
                    // Skip transparent characters
                    continue
                }
                layer.setCharacterAt(
                        // Have to swap rows and columns due to how image data is stored
                        Position.of(row, column),
                        TextCharacterBuilder.newBuilder()
                                .character(cell.character)
                                .backgroundColor(TextColorFactory.fromAWTColor(cell.backgroundColor))
                                .foregroundColor(TextColorFactory.fromAWTColor(cell.foregroundColor))
                                .build()
                )
            }
        }
        return layer
    }

    companion object {
        val TRANSPARENT_BACKGROUND = Color(255, 0, 255)

        fun fromByteBuffer(buffer: ByteBuffer): Layer {
            val width = buffer.int
            val height = buffer.int

            val cells: MutableList<Cell> = mutableListOf()
            var index = 0
            while (index < width*height) {
                cells.add(Cell.fromByteBuffer(buffer))
                index++
            }

            return Layer(width, height, cells)
        }
    }
}
