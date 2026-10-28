package org.hexworks.zircon.compose

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position

/**
 * One frame of the grid as three small RGBA images, one texel per cell, ready to be
 * uploaded as data textures for the tile shader:
 *
 * - [cells]: `r` = atlas column, `g` = atlas row, `b` = atlas id, `a` = 255
 * - [foregrounds] and [backgrounds]: `rgb` = color, `a` = 255 when opaque, 0 when transparent
 *
 * Tilesets write into it through [write]; the renderer clears it before every frame.
 * Per-frame cost scales with cells, never with pixels, which is what keeps wasm fast.
 */
public class FrameData(public val cols: Int, public val rows: Int) {

    public val cells: ByteArray = ByteArray(cols * rows * 4)
    public val foregrounds: ByteArray = ByteArray(cols * rows * 4)
    public val backgrounds: ByteArray = ByteArray(cols * rows * 4)

    /** Atlas ids that were written since the last [clear], so the renderer can skip idle atlases. */
    public val usedAtlases: MutableSet<Int> = mutableSetOf()

    public fun clear() {
        cells.fill(0)
        foregrounds.fill(0)
        backgrounds.fill(0)
        usedAtlases.clear()
    }

    /**
     * Writes the glyph at atlas cell ([slotX], [slotY]) of atlas [atlasId] with the given
     * colors. Positions outside the grid are ignored.
     */
    public fun write(position: Position, atlasId: Int, slotX: Int, slotY: Int, foreground: Color, background: Color) {
        val x = position.x
        val y = position.y
        if (x < 0 || y < 0 || x >= cols || y >= rows) return
        val i = (y * cols + x) * 4
        // Tiles arrive bottom to top. A transparent background or foreground means "keep
        // what is below", which is how layers and stacked tiles show through.
        if (!foreground.isTransparent) {
            cells[i] = slotX.toByte()
            cells[i + 1] = slotY.toByte()
            cells[i + 2] = atlasId.toByte()
            cells[i + 3] = -1
            foreground.writeTo(foregrounds, i)
            usedAtlases += atlasId
        }
        if (!background.isTransparent) {
            background.writeTo(backgrounds, i)
        }
    }

    /**
     * Writes only a background, for tiles that have nothing to draw on top (empty tiles).
     */
    public fun writeBackground(position: Position, background: Color) {
        val x = position.x
        val y = position.y
        if (x < 0 || y < 0 || x >= cols || y >= rows) return
        val i = (y * cols + x) * 4
        if (!background.isTransparent) {
            background.writeTo(backgrounds, i)
        }
    }

    private fun Color.writeTo(target: ByteArray, offset: Int) {
        if (isTransparent) {
            target[offset] = 0; target[offset + 1] = 0; target[offset + 2] = 0; target[offset + 3] = 0
        } else {
            target[offset] = red.toByte(); target[offset + 1] = green.toByte(); target[offset + 2] = blue.toByte(); target[offset + 3] = -1
        }
    }

    /** No atlas: nothing is drawn on top of the background. */
    public companion object {
        public const val NO_ATLAS: Int = 0
    }
}
