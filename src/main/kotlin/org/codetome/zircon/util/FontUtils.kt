package org.codetome.zircon.util

import java.awt.Font
import java.awt.GraphicsEnvironment
import java.awt.RenderingHints
import java.awt.font.FontRenderContext

object FontUtils {

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

    @JvmStatic
    fun isFontMonospaced(font: Font): Boolean {
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

    @JvmStatic
    fun getFontWidth(font: Font): Int {
        return font.getStringBounds("W", getFontRenderContext()).width.toInt()
    }

    @JvmStatic
    fun getFontHeight(font: Font): Int {
        return font.getStringBounds("W", getFontRenderContext()).height.toInt()
    }

    @JvmStatic
    fun getFontByName(name: String) = listOf(
            Font(name, Font.PLAIN, getFontSize())
    )

    @JvmStatic
    fun getDefaultWindowsFonts() = listOf(
            Font("Courier New", Font.PLAIN, getFontSize()),
            Font("Monospaced", Font.PLAIN, getFontSize()))

    @JvmStatic
    fun getDefaultLinuxFonts() = listOf(
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

    @JvmStatic
    fun getDefaultFonts() = listOf(Font("Monospaced", Font.PLAIN, getFontSize()))

    /**
     * Returns the default font to use depending on the platform.
     */
    @JvmStatic
    fun selectDefaultFont(): MutableList<Font> {
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

    @JvmStatic
    fun filterMonospaced(fonts: List<Font>): MutableList<Font> {
        return fonts.filter { isFontMonospaced(it) }.toMutableList()
    }

    private fun getFontRenderContext() = FontRenderContext(null,
            RenderingHints.VALUE_TEXT_ANTIALIAS_OFF,
            RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT)

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
        val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
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
}