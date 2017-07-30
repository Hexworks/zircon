package org.codetome.zircon.terminal.config

import org.codetome.zircon.font.*
import org.codetome.zircon.terminal.swing.BufferedImageSprite
import org.codetome.zircon.terminal.swing.SwingCharacterImageRenderer
import java.awt.Font
import java.awt.Graphics
import java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment
import java.awt.Image
import java.awt.RenderingHints
import java.awt.font.FontRenderContext
import java.io.File
import javax.imageio.ImageIO


/**
 * MonospaceFontRenderer configuration class for [org.codetome.zircon.terminal.swing.SwingTerminalComponent].
 */
object FontConfiguration {

    // Here we check the screen resolution on the primary monitor and make a guess at if it's high-DPI or not
    private var CHOSEN_FONT_SIZE: Int? = null

    private val MONOSPACE_CHECK_OVERRIDE = setOf(
            "VL Gothic Regular",
            "NanumGothic",
            "WenQuanYi Zen Hei Mono",
            "WenQuanYi Zen Hei",
            "AR PL UMing TW",
            "AR PL UMing HK",
            "AR PL UMing CN")

    /**
     * This is the default font settings that will be used if you don't specify anything.
     */
    @JvmStatic
    fun getDefault() = createSwingFontRendererForPhysicalFonts()

    @JvmStatic
    fun createSwingFontRendererForTileset(tileset: TilesetResource)
            : MonospaceFontRenderer<Graphics> {
        return TilesetFontRenderer<Sprite<Image>, Image, Graphics>(
                width = tileset.width,
                height = tileset.height,
                renderer = SwingCharacterImageRenderer(
                        fontWidth = tileset.width,
                        fontHeight = tileset.height),
                sprite = BufferedImageSprite(
                        tileset = ImageIO.read(File("src/main/resources/${tileset.dir}/${tileset.fileName}"))))
    }

    @JvmStatic
    @JvmOverloads
    fun createSwingFontRendererForPhysicalFonts(isAntiAliased: Boolean = false,
                                                boldMode: BoldMode = BoldMode.EVERYTHING_BUT_SYMBOLS,
                                                fontsInOrderOfPriority: MutableList<Font> = filterMonospaced(selectDefaultFont()))
            : PhysicalFontRenderer<Image, Graphics> {

        val fontWidth = getFontWidth(fontsInOrderOfPriority.first(), isAntiAliased)
        val fontHeight = getFontHeight(fontsInOrderOfPriority.first(), isAntiAliased)

        if (fontsInOrderOfPriority.isEmpty()) {
            throw IllegalArgumentException("Must pass in a valid list of fonts to FontConfiguration")
        }
        //Make sure all the fonts are monospace
        fontsInOrderOfPriority
                .filterNot { FontConfiguration.isFontMonospaced(it) }
                .forEach { throw IllegalArgumentException("MonospaceFontRenderer $it isn't monospaced!") }

        //Make sure all lower-priority fonts are less or equal in width and height, shrink if necessary
        (1..fontsInOrderOfPriority.size - 1).forEach { i ->
            var font = fontsInOrderOfPriority[i]
            while (getFontWidth(font, isAntiAliased) > fontWidth || getFontHeight(font, isAntiAliased) > fontHeight) {
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
                boldMode = boldMode,
                characterImageRenderer = SwingCharacterImageRenderer(
                        fontWidth = fontWidth,
                        fontHeight = fontHeight),
                width = fontWidth,
                height = fontHeight)
    }


    //Monospaced can look pretty bad on Windows, so let's override it
    private fun getDefaultWindowsFonts() = listOf(
            Font("Courier New", Font.PLAIN, getFontSize()),
            Font("Monospaced", Font.PLAIN, getFontSize()))

    //Below, these should be redundant (Monospaced is supposed to catch-all)
    // but Java 6 seems to have issues with finding monospaced fonts sometimes
    private fun getDefaultLinuxFonts() = listOf(
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
    private fun selectDefaultFont(): MutableList<Font> {
        val osName = System.getProperty("os.name", "").toLowerCase()
        if (osName.contains("win")) {
            val windowsFonts = getDefaultWindowsFonts()
            return windowsFonts.toMutableList()
        } else if (osName.contains("linux")) {
            val linuxFonts = getDefaultLinuxFonts()
            return linuxFonts.toMutableList()
        } else {
            val defaultFonts = getDefaultFonts()
            return defaultFonts.toMutableList()
        }
    }

    private fun filterMonospaced(fonts: List<Font>): MutableList<Font> {
        return fonts.filter { isFontMonospaced(it) }.toMutableList()
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

    private fun getFontWidth(font: Font, antiAliased: Boolean): Int {
        return font.getStringBounds("W", getFontRenderContext(antiAliased)).width.toInt()
    }

    private fun getFontHeight(font: Font, antiAliased: Boolean): Int {
        return font.getStringBounds("W", getFontRenderContext(antiAliased)).height.toInt()
    }

    private fun getFontRenderContext(antiAliased: Boolean) = FontRenderContext(null,
            if (antiAliased) {
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
            } else {
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF
            },
            RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT)

}
