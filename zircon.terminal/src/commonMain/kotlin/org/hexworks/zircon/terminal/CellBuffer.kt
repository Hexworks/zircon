package org.hexworks.zircon.terminal

import org.hexworks.zircon.api.color.Color

/**
 * One frame of the terminal: a code point and packed `0xRRGGBB` foreground and background
 * per cell, in flat arrays so a 32k-cell frame costs three array writes per tile and no
 * objects. This is the render context the [TerminalRenderer] draws into.
 */
public class CellBuffer(public val width: Int, public val height: Int) {

    public val codes: IntArray = IntArray(width * height) { SPACE }
    public val foregrounds: IntArray = IntArray(width * height) { DEFAULT_FOREGROUND }
    public val backgrounds: IntArray = IntArray(width * height) { DEFAULT_BACKGROUND }

    public fun index(x: Int, y: Int): Int = y * width + x

    /** Resets every cell to a space on the default background. */
    public fun clear() {
        codes.fill(SPACE)
        foregrounds.fill(DEFAULT_FOREGROUND)
        backgrounds.fill(DEFAULT_BACKGROUND)
    }

    public fun copyFrom(other: CellBuffer) {
        other.codes.copyInto(codes)
        other.foregrounds.copyInto(foregrounds)
        other.backgrounds.copyInto(backgrounds)
    }

    public companion object {
        public const val SPACE: Int = ' '.code
        public const val DEFAULT_FOREGROUND: Int = 0xFFFFFF
        public const val DEFAULT_BACKGROUND: Int = 0x000000

        public fun pack(color: Color): Int = (color.red shl 16) or (color.green shl 8) or color.blue
    }
}
