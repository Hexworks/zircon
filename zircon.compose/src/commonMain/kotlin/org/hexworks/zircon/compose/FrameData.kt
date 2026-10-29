package org.hexworks.zircon.compose

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position

/**
 * One frame of the grid as three small RGBA images, one texel per cell, ready to be
 * uploaded as data textures for the tile shader:
 *
 * - [cells]: `r` = atlas column, `g` = atlas row, `b` = atlas id, `a` = 255
 * - [foregrounds]: `rgb` = tint, `a` = 255 for a tinted mask (glyph), 128 for a coloured
 *   sprite, 0 for nothing on top
 * - [backgrounds]: `rgb` = color, `a` = 255 when opaque, 0 when transparent
 *
 * Tilesets write into it through [writeGlyph] and [writeSprite]; the renderer clears it
 * before every frame. Per-frame cost scales with cells, never with pixels.
 */
public class FrameData(public val cols: Int, public val rows: Int) {

    public val cells: ByteArray = ByteArray(cols * rows * 4)
    public val foregrounds: ByteArray = ByteArray(cols * rows * 4)
    public val backgrounds: ByteArray = ByteArray(cols * rows * 4)

    /** Atlas ids written since the last [clear], so the renderer can skip idle atlases. */
    public val usedAtlases: MutableSet<Int> = mutableSetOf()
    private val atlasSeen = BooleanArray(256) // atlas ids fit a byte, see [writeCell]

    public fun clear() {
        cells.fill(0)
        foregrounds.fill(0)
        backgrounds.fill(0)
        usedAtlases.clear()
        atlasSeen.fill(false)
    }

    /**
     * Writes a glyph: atlas cell ([slotX], [slotY]) of atlas [atlasId] used as a mask and
     * tinted with [foreground], over [background]. Tiles arrive bottom to top, and a
     * transparent colour keeps what is below, which is how layers show through.
     */
    public fun writeGlyph(position: Position, atlasId: Int, slotX: Int, slotY: Int, foreground: Color, background: Color) {
        val i = indexOf(position) ?: return
        if (!foreground.isTransparent) {
            writeCell(i, atlasId, slotX, slotY)
            foregrounds[i] = foreground.red.toByte()
            foregrounds[i + 1] = foreground.green.toByte()
            foregrounds[i + 2] = foreground.blue.toByte()
            foregrounds[i + 3] = -1
        }
        writeBackground(i, background)
    }

    /**
     * Writes a sprite: atlas cell ([slotX], [slotY]) of atlas [atlasId] drawn with its own
     * colours, over [background].
     */
    public fun writeSprite(position: Position, atlasId: Int, slotX: Int, slotY: Int, background: Color = Color.TRANSPARENT) {
        val i = indexOf(position) ?: return
        writeCell(i, atlasId, slotX, slotY)
        foregrounds[i] = -1
        foregrounds[i + 1] = -1
        foregrounds[i + 2] = -1
        foregrounds[i + 3] = SPRITE_MODE
        writeBackground(i, background)
    }

    /**
     * Writes only a background, for tiles with nothing to draw on top.
     */
    public fun writeBackground(position: Position, background: Color) {
        val i = indexOf(position) ?: return
        writeBackground(i, background)
    }

    private fun indexOf(position: Position): Int? {
        val x = position.x
        val y = position.y
        return if (x < 0 || y < 0 || x >= cols || y >= rows) null else (y * cols + x) * 4
    }

    private fun writeCell(i: Int, atlasId: Int, slotX: Int, slotY: Int) {
        cells[i] = slotX.toByte()
        cells[i + 1] = slotY.toByte()
        cells[i + 2] = atlasId.toByte()
        cells[i + 3] = -1
        if (!atlasSeen[atlasId]) {
            atlasSeen[atlasId] = true
            usedAtlases += atlasId
        }
    }

    private fun writeBackground(i: Int, background: Color) {
        if (background.isTransparent) return
        backgrounds[i] = background.red.toByte()
        backgrounds[i + 1] = background.green.toByte()
        backgrounds[i + 2] = background.blue.toByte()
        backgrounds[i + 3] = -1
    }

    public companion object {
        /** No atlas: nothing is drawn on top of the background. */
        public const val NO_ATLAS: Int = 0
        private const val SPRITE_MODE: Byte = 128.toByte()
    }
}
