package org.codetome.zircon.builder

import org.codetome.zircon.font.PhysicalFontRenderer
import org.codetome.zircon.terminal.swing.SwingCharacterImageRenderer
import org.codetome.zircon.util.FontUtils
import java.awt.Font
import java.awt.Graphics
import java.awt.image.BufferedImage

class SwingPhysicalFontsBuilder {

    private var antiAliased: Boolean = false
    private var fontsInOrderOfPriority: MutableList<Font> = FontUtils.filterMonospaced(FontUtils.selectDefaultFont())

    fun antiAliased(antiAliased: Boolean) = also {
        this.antiAliased = antiAliased
    }

    /**
     * You can set the fonts you want to use here (arbitrary [java.awt.Font]s can be
     * loaded as well and supplied here).
     */
    fun fontsInOrderOfPriority(fontsInOrderOfPriority: List<Font>) = also {
        this.fontsInOrderOfPriority.clear()
        this.fontsInOrderOfPriority.addAll(fontsInOrderOfPriority)
    }

    fun build(): PhysicalFontRenderer<BufferedImage, Graphics> {

        val fontWidth = FontUtils.getFontWidth(fontsInOrderOfPriority.first())
        val fontHeight = FontUtils.getFontHeight(fontsInOrderOfPriority.first())

        if (fontsInOrderOfPriority.isEmpty()) {
            throw IllegalArgumentException("Must pass in a valid list of fonts to FontUtils")
        }
        //Make sure all the fonts are monospace
        fontsInOrderOfPriority
                .filterNot { FontUtils.isFontMonospaced(it) }
                .forEach { throw IllegalArgumentException("FontRenderer $it isn't monospaced!") }

        //Make sure all lower-priority fonts are less or equal in width and height, shrink if necessary
        (1..fontsInOrderOfPriority.size - 1).forEach { i ->
            var font = fontsInOrderOfPriority[i]
            while (FontUtils.getFontWidth(font) > fontWidth || FontUtils.getFontHeight(font) > fontHeight) {
                val newSize = font.size2D - 0.5f
                if (newSize < 0.01) {
                    throw IllegalStateException("Unable to shrink font " + (i + 1) + " to fit the size of highest priority font " + fontsInOrderOfPriority[0])
                }
                font = font.deriveFont(newSize)
                fontsInOrderOfPriority[i] = font
            }
        }
        return PhysicalFontRenderer(
                fontsInOrderOfPriority = fontsInOrderOfPriority,
                characterImageRenderer = SwingCharacterImageRenderer(
                        fontWidth = fontWidth,
                        fontHeight = fontHeight),
                width = fontWidth,
                height = fontHeight,
                antiAliased = antiAliased)
    }
}