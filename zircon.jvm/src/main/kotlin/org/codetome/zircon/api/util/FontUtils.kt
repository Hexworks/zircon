package org.codetome.zircon.api.util

import java.awt.Font
import java.awt.RenderingHints
import java.awt.font.FontRenderContext

/**
 * Convenience functions for [Font]s.
 */
object FontUtils {

    /**
     * Tells whether the given [Font] is monospaced or not.
     */
    @JvmStatic
    fun isFontMonospaced(font: Font): Boolean {
        val frc = getFontRenderContext()
        val iBounds = font.getStringBounds("i", frc)
        val mBounds = font.getStringBounds("W", frc)
        return iBounds.width == mBounds.width
    }

    /**
     * Calculates the width of a given [Font] in pixels.
     */
    @JvmStatic
    fun getFontWidth(font: Font): Int {
        return font.getStringBounds("W", getFontRenderContext()).width.toInt()
    }

    /**
     * Calculates the height of a given [Font] in pixels.
     */
    @JvmStatic
    fun getFontHeight(font: Font): Int {
        return font.getStringBounds("W", getFontRenderContext()).height.toInt()
    }

    private fun getFontRenderContext() = FontRenderContext(null,
            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF,
            RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT)
}
