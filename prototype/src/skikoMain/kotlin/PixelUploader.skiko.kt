import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ImageInfo

actual class PixelUploader actual constructor(width: Int, height: Int) : AutoCloseable {
    private val info = ImageInfo.makeN32(width, height, ColorAlphaType.PREMUL)
    private val rowBytes = width * 4
    private var previous: Bitmap? = null

    actual fun upload(pixels: ByteArray): ImageBitmap {
        previous?.close()
        val bitmap = Bitmap()
        check(bitmap.installPixels(info, pixels, rowBytes)) { "installPixels failed" }
        previous = bitmap
        return bitmap.asComposeImageBitmap()
    }

    actual override fun close() {
        previous?.close()
        previous = null
    }
}
