package org.hexworks.zircon.renderer.compose

import androidx.compose.ui.graphics.ImageBitmap
import org.hexworks.zircon.renderer.compose.tileset.SkiaGlyph
import org.hexworks.zircon.renderer.compose.tileset.SkiaPixelBuffer

/**
 * JVM implementation of ComposeContext.
 * Wraps SkiaPixelBuffer for high-performance rendering.
 *
 * Each tileset is responsible for managing its own glyph cache or sprite data.
 */
actual class ComposeContext(
    val pixelBuffer: SkiaPixelBuffer,
    actual val tileWidth: Int,
    actual val tileHeight: Int
) : AutoCloseable {

    actual fun fillRect(x: Int, y: Int, w: Int, h: Int, argb: Int) {
        pixelBuffer.fillRect(x, y, w, h, argb)
    }

    actual fun blendImage(
        imageData: ByteArray,
        imageWidth: Int,
        imageHeight: Int,
        destX: Int,
        destY: Int,
        tintArgb: Int?
    ) {
        pixelBuffer.blendImage(imageData, imageWidth, imageHeight, destX, destY, tintArgb)
    }

    /**
     * Blends a pre-rendered glyph onto the buffer.
     * This is called by tilesets that manage their own glyph caches.
     */
    fun blendGlyph(glyph: SkiaGlyph, x: Int, y: Int, foregroundArgb: Int) {
        pixelBuffer.blendGlyph(glyph, x, y, foregroundArgb)
    }

    /**
     * Clears the pixel buffer with the specified color.
     */
    fun clear(argb: Int) {
        pixelBuffer.clear(argb)
    }

    /**
     * Converts the pixel buffer to an ImageBitmap for Compose rendering.
     */
    fun toImageBitmap(): ImageBitmap {
        return pixelBuffer.toImageBitmap()
    }

    override fun close() {
        pixelBuffer.close()
    }
}