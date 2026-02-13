package org.hexworks.zircon.renderer.compose.tileset

import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.Data
import org.jetbrains.skia.Font
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.ImageInfo
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Surface
import org.jetbrains.skia.Typeface
import org.jetbrains.skia.Color as SkiaColor

/**
 * Pre-rendered glyph with alpha data for fast CPU blending.
 */
class SkiaGlyph(
    val width: Int,
    val height: Int,
    val alphas: ByteArray
)

/**
 * Cache of pre-rendered glyphs using Skia.
 * Stores alpha data for fast CPU-based color blending.
 */
class SkiaGlyphCache(
    val tileWidth: Int,
    val tileHeight: Int,
    typeface: Typeface
) : AutoCloseable {

    private val glyphs = mutableMapOf<Char, SkiaGlyph>()
    private val font = Font(typeface, tileHeight.toFloat())
    private val defaultGlyph: SkiaGlyph

    init {
        // Pre-render printable ASCII characters
        for (char in ' '..'~') {
            glyphs[char] = renderGlyph(char)
        }
        defaultGlyph = glyphs[' '] ?: renderGlyph(' ')
    }

    private fun renderGlyph(char: Char): SkiaGlyph {
        val surface = Surface.makeRasterN32Premul(tileWidth, tileHeight)
        val paint = Paint()
        try {
            val canvas = surface.canvas
            paint.color = SkiaColor.WHITE
            paint.isAntiAlias = true

            val metrics = font.metrics
            val text = char.toString()
            val textWidth = font.measureTextWidth(text)

            val x = (tileWidth - textWidth) / 2
            val y = -metrics.ascent

            canvas.drawString(text, x, y, font, paint)

            val image = surface.makeImageSnapshot()
            try {
                val bitmap = Bitmap()
                try {
                    bitmap.allocPixels(ImageInfo.makeN32(tileWidth, tileHeight, ColorAlphaType.PREMUL))
                    image.readPixels(bitmap, 0, 0)

                    val alphas = ByteArray(tileWidth * tileHeight)
                    val pixels = bitmap.readPixels(
                        ImageInfo.makeN32(tileWidth, tileHeight, ColorAlphaType.PREMUL),
                        tileWidth * 4, 0, 0
                    )
                    if (pixels != null) {
                        for (i in 0 until tileWidth * tileHeight) {
                            alphas[i] = pixels[i * 4 + 3]
                        }
                    }

                    return SkiaGlyph(tileWidth, tileHeight, alphas)
                } finally {
                    bitmap.close()
                }
            } finally {
                image.close()
            }
        } finally {
            paint.close()
            surface.close()
        }
    }

    /**
     * Gets a glyph for the given character, rendering it lazily if not cached.
     */
    fun getGlyph(char: Char): SkiaGlyph {
        return glyphs.getOrPut(char) { renderGlyph(char) }
    }

    override fun close() {
        font.close()
    }
}

/**
 * Creates a Typeface from font bytes using FontMgr.
 */
fun createTypeface(fontBytes: ByteArray): Typeface {
    val data = Data.makeFromBytes(fontBytes)
    try {
        val fontMgr = FontMgr.default
        return fontMgr.makeFromData(data) ?: error("Failed to create Typeface from font data")
    } finally {
        data.close()
    }
}

/**
 * Creates a default monospace Typeface using FontMgr.
 */
fun createDefaultTypeface(): Typeface {
    val fontMgr = FontMgr.default
    val styleSet = fontMgr.matchFamily("monospace")
    try {
        val typeface = styleSet.matchStyle(FontStyle.NORMAL)
        if (typeface != null) {
            return typeface
        }
    } finally {
        styleSet.close()
    }

    // Fallback to default font family
    val fallbackStyleSet = fontMgr.matchFamily(null)
    try {
        return fallbackStyleSet.matchStyle(FontStyle.NORMAL)
            ?: error("Failed to create default Typeface")
    } finally {
        fallbackStyleSet.close()
    }
}
