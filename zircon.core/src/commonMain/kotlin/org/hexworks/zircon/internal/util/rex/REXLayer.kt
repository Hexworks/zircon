package org.hexworks.zircon.internal.util.rex

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.TilesetResource

private val REX_TRANSPARENT_BACKGROUND = TileColor.create(255, 0, 255)

/**
 * Represents a REX Paint Layer, which contains its size information (width, height) and a [List] of [REXCell]s.
 */
data class REXLayer(
    val width: Int,
    val height: Int,
    val cells: List<REXCell>
) {
    /**
     * Returns itself as a [REXLayer].
     */
    fun toLayer(tileset: TilesetResource): Layer {
        val layer = layer {
            this.tileset = tileset
            size = Size.create(width, height)
        }

        for (y in 0 until height) {
            for (x in 0 until width) {
                // Have to swap x and y due to how image data is stored
                val cell = cells[x * height + y]
                if (cell.backgroundColor == REX_TRANSPARENT_BACKGROUND) {
                    // Skip transparent characters
                    continue
                }
                layer.draw(
                    tile = characterTile {
                        character = cell.character
                        withStyleSet {
                            backgroundColor = cell.backgroundColor
                            foregroundColor = cell.foregroundColor
                        }
                    },
                    drawPosition = Position.create(x, y)
                )
            }
        }
        return layer
    }
}
