package org.codetome.zircon.terminal.config

import org.codetome.zircon.Symbols
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.terminal.config.BoldMode.EVERYTHING
import org.codetome.zircon.terminal.config.BoldMode.EVERYTHING_BUT_SYMBOLS
import java.awt.Font
import java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment
import java.awt.RenderingHints
import java.awt.font.FontRenderContext

/**
 * Font configuration class for [org.codetome.zircon.terminal.swing.SwingTerminalComponent].
 */
class TerminalFontConfiguration(
        internal val isAntiAliased: Boolean,
        private val boldMode: BoldMode,
        vararg fontsInOrderOfPriority: Font) {

    private val fontsInOrderOfPriority: MutableList<Font>
    private val fontWidth: Int
    private val fontHeight: Int

    fun getFontWidth() = fontWidth

    fun getFontHeight() = fontHeight

    init {
        if (fontsInOrderOfPriority.isEmpty()) {
            throw IllegalArgumentException("Must pass in a valid list of fonts to TerminalFontConfiguration")
        }
        this.fontsInOrderOfPriority = mutableListOf(*fontsInOrderOfPriority)
        this.fontWidth = getFontWidth(this.fontsInOrderOfPriority[0])
        this.fontHeight = getFontHeight(this.fontsInOrderOfPriority[0])

        //Make sure all the fonts are monospace
        this.fontsInOrderOfPriority
                .filterNot { isFontMonospaced(it) }
                .forEach { throw IllegalArgumentException("Font $it isn't monospaced!") }

        //Make sure all lower-priority fonts are less or equal in width and height, shrink if necessary
        (1..this.fontsInOrderOfPriority.size - 1).forEach { i ->
            var font = this.fontsInOrderOfPriority[i]
            while (getFontWidth(font) > fontWidth || getFontHeight(font) > fontHeight) {
                val newSize = font.size2D - 0.5f
                if (newSize < 0.01) {
                    throw IllegalStateException("Unable to shrink font " + (i + 1) + " to fit the size of highest priority font " + this.fontsInOrderOfPriority[0])
                }
                font = font.deriveFont(newSize)
                this.fontsInOrderOfPriority[i] = font
            }
        }
    }

    /**
     * Given a certain character, return the font to use for drawing it.
     */
    fun getFontForCharacter(character: TextCharacter): Font {
        var normalFont = getFontForCharacter(character.getCharacter())
        if (boldMode == EVERYTHING || boldMode == EVERYTHING_BUT_SYMBOLS && isNotASymbol(character.getCharacter())) {
            if (character.isBold()) {
                normalFont = normalFont.deriveFont(Font.BOLD)
            }
        }
        if (character.isItalic()) {
            normalFont = normalFont.deriveFont(Font.ITALIC)
        }
        return normalFont
    }

    private fun getFontForCharacter(c: Char): Font {
        //No available font here, what to do...?
        return fontsInOrderOfPriority.firstOrNull { it.canDisplay(c) }
                ?: fontsInOrderOfPriority[0]
    }

    private fun getFontWidth(font: Font): Int {
        return font.getStringBounds("W", getFontRenderContext()).width.toInt()
    }

    private fun getFontHeight(font: Font): Int {
        return font.getStringBounds("W", getFontRenderContext()).height.toInt()
    }

    private fun getFontRenderContext() = FontRenderContext(null,
            if (isAntiAliased) {
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            } else {
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF
            },
            RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT)

    private fun isNotASymbol(character: Char): Boolean {
        return !Companion.SYMBOLS_CACHE.contains(character)
    }

    companion object {
        /**
         * This is the default font settings that will be used if you don't specify anything.
         */
        @JvmStatic
        fun getDefault() = newInstance(*filterMonospaced(*selectDefaultFont()))

        /**
         * Creates a new font configuration from a list of fonts in order of priority. This works by having the terminal
         * attempt to draw each character with the fonts in the order they are specified in and stop once we find a font
         * that can actually draw the character. For ASCII characters, it's very likely that the first font will always be
         * used.
         */
        @JvmStatic
        fun newInstance(vararg fontsInOrderOfPriority: Font): TerminalFontConfiguration {
            return TerminalFontConfiguration(false, EVERYTHING_BUT_SYMBOLS, *fontsInOrderOfPriority)
        }

        private val MONOSPACE_CHECK_OVERRIDE = setOf(
                "VL Gothic Regular",
                "NanumGothic",
                "WenQuanYi Zen Hei Mono",
                "WenQuanYi Zen Hei",
                "AR PL UMing TW",
                "AR PL UMing HK",
                "AR PL UMing CN"
        )

        //Monospaced can look pretty bad on Windows, so let's override it
        private fun getDefaultWindowsFonts() = listOf(
                Font("Courier New", Font.PLAIN, getFontSize()),
                Font("Monospaced", Font.PLAIN, getFontSize()))

        //Below, these should be redundant (Monospaced is supposed to catch-all)
        // but Java 6 seems to have issues with finding monospaced fonts sometimes
        private fun getDefaultLinuxFonts() = setOf(
                Font("DejaVu Sans Mono", Font.PLAIN, getFontSize()),
                Font("Monospaced", Font.PLAIN, getFontSize()),
                Font("Ubuntu Mono", Font.PLAIN, getFontSize()),
                Font("FreeMono", Font.PLAIN, getFontSize()),
                Font("Liberation Mono", Font.PLAIN, getFontSize()),
                Font("VL Gothic Regular", Font.PLAIN, getFontSize()),
                Font("NanumGothic", Font.PLAIN, getFontSize()),
                Font("WenQuanYi Zen Hei Mono", Font.PLAIN, getFontSize()),
                Font("WenQuanYi Zen Hei", Font.PLAIN, getFontSize()),
                Font("AR PL UMing TW", Font.PLAIN, getFontSize()),
                Font("AR PL UMing HK", Font.PLAIN, getFontSize()),
                Font("AR PL UMing CN", Font.PLAIN, getFontSize()))

        private fun getDefaultFonts() = listOf(Font("Monospaced", Font.PLAIN, getFontSize()))

        // Here we check the screen resolution on the primary monitor and make a guess at if it's high-DPI or not
        private var CHOSEN_FONT_SIZE: Int? = null

        // Source: http://stackoverflow.com/questions/3680221/how-can-i-get-the-monitor-size-in-java
        // Assume the first GraphicsDevice is the primary screen (this isn't always correct but what to do?)
        // Warning, there could be printers coming back here according to JavaDoc! Hopefully Java is reasonable and
        // passes them in after the real monitor(s).
        // If the width is wider than Full HD (1080p, or 1920x1080), then assume it's high-DPI
        // If no size was picked, default to 14
        private fun getFontSize(): Int {
            if (CHOSEN_FONT_SIZE != null) {
                return CHOSEN_FONT_SIZE!!
            }
            val ge = getLocalGraphicsEnvironment()
            val gs = ge.screenDevices
            if (gs.isNotEmpty()) {
                val primaryMonitorWidth = gs[0].displayMode.width
                if (primaryMonitorWidth > 2000) {
                    CHOSEN_FONT_SIZE = 28
                }
            }
            if (CHOSEN_FONT_SIZE == null) {
                CHOSEN_FONT_SIZE = 14
            }
            return CHOSEN_FONT_SIZE!!
        }

        /**
         * Returns the default font to use depending on the platform.
         */
        private fun selectDefaultFont(): Array<Font> {
            val osName = System.getProperty("os.name", "").toLowerCase()
            if (osName.contains("win")) {
                val windowsFonts = getDefaultWindowsFonts()
                return windowsFonts.toTypedArray()
            } else if (osName.contains("linux")) {
                val linuxFonts = getDefaultLinuxFonts()
                return linuxFonts.toTypedArray()
            } else {
                val defaultFonts = getDefaultFonts()
                return defaultFonts.toTypedArray()
            }
        }

        /**
         * Given an array of fonts, returns another array with only the ones that are monospaced. The fonts in the result
         * will have the same order as in which they came in. A font is considered monospaced if the width of 'i' and 'W' is
         * the same.
         */
        fun filterMonospaced(vararg fonts: Font): Array<Font> {
            val result = java.util.ArrayList<Font>(fonts.size)
            fonts.filterTo(result) { isFontMonospaced(it) }
            return result.toTypedArray()
        }

        private fun isFontMonospaced(font: Font): Boolean {
            if (MONOSPACE_CHECK_OVERRIDE.contains(font.name)) {
                return true
            }
            val frc = FontRenderContext(null,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF,
                    RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT)
            val iBounds = font.getStringBounds("i", frc)
            val mBounds = font.getStringBounds("W", frc)
            return iBounds.width == mBounds.width
        }


        private val SYMBOLS_CACHE = Symbols::class.java.fields
                .filter { it.type == Char::class.javaPrimitiveType && it.modifiers and java.lang.reflect.Modifier.FINAL != 0 && it.modifiers and java.lang.reflect.Modifier.STATIC != 0 }
                .map {
                    try {
                        it.getChar(null)
                    } catch (ignore: Exception) {
                        throw RuntimeException("This should never have happened!")
                    }
                }.toSet()

    }
}
