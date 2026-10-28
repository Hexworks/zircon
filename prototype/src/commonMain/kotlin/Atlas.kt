import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toPixelMap
import org.jetbrains.compose.resources.decodeToImageBitmap
import proto.Res

/**
 * A 16x16 CP437 sprite sheet with `tile`-sized glyphs.
 * [image] feeds the GPU path, [alphas] (one byte per pixel per glyph) feeds the CPU path.
 */
class Atlas(val image: ImageBitmap, val tile: Int) {
    val alphas: Array<ByteArray> = Array(256) { ByteArray(tile * tile) }

    init {
        val pixels = image.toPixelMap()
        for (code in 0 until 256) {
            val gx = (code % 16) * tile
            val gy = (code / 16) * tile
            val out = alphas[code]
            for (y in 0 until tile) {
                for (x in 0 until tile) {
                    val c = pixels[gx + x, gy + y]
                    // Glyphs are white on transparent; alpha alone describes the shape.
                    out[y * tile + x] = (c.alpha * 255f + 0.5f).toInt().toByte()
                }
            }
        }
    }

    companion object {
        suspend fun load(): Atlas {
            val bytes = Res.readBytes("files/cp437_8x8.png")
            return Atlas(bytes.decodeToImageBitmap(), tile = 8)
        }
    }
}
