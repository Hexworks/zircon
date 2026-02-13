package org.hexworks.zircon.renderer.compose.tileset

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo

/**
 * High-performance pixel buffer renderer using direct pixel manipulation.
 * Uses CPU-based rendering with a single GPU upload per frame.
 */
class SkiaPixelBuffer(
    val width: Int,
    val height: Int
) : AutoCloseable {

    private val imageInfo = ImageInfo.makeN32(width, height, ColorAlphaType.PREMUL)
    private val rowBytes = width * 4
    internal val pixels = ByteArray(width * height * 4)

    // Track the previous image AND bitmap to close them when creating new ones
    private var previousImage: Image? = null
    private var previousBitmap: Bitmap? = null

    /**
     * Clears the entire buffer with the specified color.
     */
    fun clear(argb: Int) {
        fillRect(0, 0, width, height, argb)
    }

    /**
     * Fills a rectangular region with a solid color.
     */
    fun fillRect(x: Int, y: Int, w: Int, h: Int, argb: Int) {
        val a = ((argb shr 24) and 0xFF).toByte()
        val r = ((argb shr 16) and 0xFF).toByte()
        val g = ((argb shr 8) and 0xFF).toByte()
        val b = (argb and 0xFF).toByte()

        val x2 = minOf(x + w, width)
        val y2 = minOf(y + h, height)
        val x1 = maxOf(x, 0)
        val y1 = maxOf(y, 0)

        for (py in y1 until y2) {
            var offset = (py * width + x1) * 4
            for (px in x1 until x2) {
                pixels[offset] = b
                pixels[offset + 1] = g
                pixels[offset + 2] = r
                pixels[offset + 3] = a
                offset += 4
            }
        }
    }

    /**
     * Blends a glyph onto the buffer using CPU alpha blending.
     */
    fun blendGlyph(glyph: SkiaGlyph, x: Int, y: Int, foregroundArgb: Int) {
        val fgR = (foregroundArgb shr 16) and 0xFF
        val fgG = (foregroundArgb shr 8) and 0xFF
        val fgB = foregroundArgb and 0xFF

        val glyphWidth = glyph.width
        val glyphHeight = glyph.height
        val alphas = glyph.alphas

        for (gy in 0 until glyphHeight) {
            val py = y + gy
            if (py < 0 || py >= height) continue

            val rowOffset = py * width * 4
            val glyphRowOffset = gy * glyphWidth

            for (gx in 0 until glyphWidth) {
                val px = x + gx
                if (px < 0 || px >= width) continue

                val alpha = alphas[glyphRowOffset + gx].toInt() and 0xFF
                if (alpha == 0) continue

                val pixelOffset = rowOffset + px * 4

                if (alpha == 255) {
                    pixels[pixelOffset] = fgB.toByte()
                    pixels[pixelOffset + 1] = fgG.toByte()
                    pixels[pixelOffset + 2] = fgR.toByte()
                    pixels[pixelOffset + 3] = 0xFF.toByte()
                } else {
                    val bgB = pixels[pixelOffset].toInt() and 0xFF
                    val bgG = pixels[pixelOffset + 1].toInt() and 0xFF
                    val bgR = pixels[pixelOffset + 2].toInt() and 0xFF

                    val invAlpha = 255 - alpha
                    val r = (fgR * alpha + bgR * invAlpha) / 255
                    val g = (fgG * alpha + bgG * invAlpha) / 255
                    val b = (fgB * alpha + bgB * invAlpha) / 255

                    pixels[pixelOffset] = b.toByte()
                    pixels[pixelOffset + 1] = g.toByte()
                    pixels[pixelOffset + 2] = r.toByte()
                    pixels[pixelOffset + 3] = 0xFF.toByte()
                }
            }
        }
    }

    /**
     * Blends image data onto the buffer using CPU alpha blending.
     * The imageData is expected to be in BGRA format.
     */
    fun blendImage(
        imageData: ByteArray,
        imageWidth: Int,
        imageHeight: Int,
        destX: Int,
        destY: Int,
        tintArgb: Int? = null
    ) {
        val tintR = tintArgb?.let { (it shr 16) and 0xFF }
        val tintG = tintArgb?.let { (it shr 8) and 0xFF }
        val tintB = tintArgb?.let { it and 0xFF }

        for (iy in 0 until imageHeight) {
            val py = destY + iy
            if (py < 0 || py >= height) continue

            val rowOffset = py * width * 4
            val imageRowOffset = iy * imageWidth * 4

            for (ix in 0 until imageWidth) {
                val px = destX + ix
                if (px < 0 || px >= width) continue

                val imageOffset = imageRowOffset + ix * 4
                val srcB = imageData[imageOffset].toInt() and 0xFF
                val srcG = imageData[imageOffset + 1].toInt() and 0xFF
                val srcR = imageData[imageOffset + 2].toInt() and 0xFF
                val alpha = imageData[imageOffset + 3].toInt() and 0xFF

                if (alpha == 0) continue

                val pixelOffset = rowOffset + px * 4

                // Apply tint if specified
                val finalR = if (tintR != null) (srcR * tintR) / 255 else srcR
                val finalG = if (tintG != null) (srcG * tintG) / 255 else srcG
                val finalB = if (tintB != null) (srcB * tintB) / 255 else srcB

                if (alpha == 255) {
                    pixels[pixelOffset] = finalB.toByte()
                    pixels[pixelOffset + 1] = finalG.toByte()
                    pixels[pixelOffset + 2] = finalR.toByte()
                    pixels[pixelOffset + 3] = 0xFF.toByte()
                } else {
                    val bgB = pixels[pixelOffset].toInt() and 0xFF
                    val bgG = pixels[pixelOffset + 1].toInt() and 0xFF
                    val bgR = pixels[pixelOffset + 2].toInt() and 0xFF

                    val invAlpha = 255 - alpha
                    val r = (finalR * alpha + bgR * invAlpha) / 255
                    val g = (finalG * alpha + bgG * invAlpha) / 255
                    val b = (finalB * alpha + bgB * invAlpha) / 255

                    pixels[pixelOffset] = b.toByte()
                    pixels[pixelOffset + 1] = g.toByte()
                    pixels[pixelOffset + 2] = r.toByte()
                    pixels[pixelOffset + 3] = 0xFF.toByte()
                }
            }
        }
    }

    /**
     * Converts the pixel buffer to an ImageBitmap for Compose.
     * Uses installPixels to skip the intermediate Image, reducing overhead.
     */
    fun toImageBitmap(): ImageBitmap {
        // Close previous bitmap before creating new one
        previousBitmap?.close()

        // Create bitmap and install pixels directly (no intermediate Image needed)
        val bitmap = Bitmap()
        val installed = bitmap.installPixels(imageInfo, pixels, rowBytes)
        if (!installed) {
            // Fallback to the Image-based approach if installPixels fails
            previousImage?.close()
            val image = Image.makeRaster(imageInfo, pixels, rowBytes)
            previousImage = image
            bitmap.allocPixels(imageInfo)
            image.readPixels(bitmap, 0, 0)
        }
        previousBitmap = bitmap

        return bitmap.asComposeImageBitmap()
    }

    override fun close() {
        previousBitmap?.close()
        previousBitmap = null
        previousImage?.close()
        previousImage = null
    }
}
