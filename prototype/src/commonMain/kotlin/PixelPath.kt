import androidx.compose.ui.graphics.ImageBitmap

/**
 * Path B: compose the whole frame on the CPU into a premultiplied BGRA byte buffer,
 * then hand it to the platform for one texture upload per frame.
 */
class PixelPath(val width: Int, val height: Int) : AutoCloseable {
    val pixels = ByteArray(width * height * 4)
    private val uploader = PixelUploader(width, height)

    fun fill(grid: Grid, atlas: Atlas) {
        val t = atlas.tile
        val stride = width * 4
        var i = 0
        for (row in 0 until grid.rows) {
            val y0 = row * t
            for (col in 0 until grid.cols) {
                val x0 = col * t
                val bg = grid.bg[i]
                val fg = grid.fg[i]
                val bgB = bg.toByte()
                val bgG = (bg shr 8).toByte()
                val bgR = (bg shr 16).toByte()
                val fgB = fg.toByte()
                val fgG = (fg shr 8).toByte()
                val fgR = (fg shr 16).toByte()
                val glyph = atlas.alphas[grid.chars[i]]
                var g = 0
                for (gy in 0 until t) {
                    var p = (y0 + gy) * stride + x0 * 4
                    for (gx in 0 until t) {
                        // No transparency in 2.0: glyph alpha is a hard mask (alpha >= 128 is set,
                        // which as a signed byte is negative).
                        if (glyph[g] < 0) {
                            pixels[p] = fgB; pixels[p + 1] = fgG; pixels[p + 2] = fgR
                        } else {
                            pixels[p] = bgB; pixels[p + 1] = bgG; pixels[p + 2] = bgR
                        }
                        pixels[p + 3] = -1
                        p += 4
                        g++
                    }
                }
                i++
            }
        }
    }

    fun upload(): ImageBitmap = uploader.upload(pixels)

    override fun close() = uploader.close()
}

/** Wraps a CPU buffer as an [ImageBitmap]; one implementation per graphics backend. */
expect class PixelUploader(width: Int, height: Int) : AutoCloseable {
    fun upload(pixels: ByteArray): ImageBitmap
    override fun close()
}
