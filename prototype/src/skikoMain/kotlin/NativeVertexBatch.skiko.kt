import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.nativeCanvas
import org.jetbrains.skia.BlendMode
import org.jetbrains.skia.FilterTileMode
import org.jetbrains.skia.Image
import org.jetbrains.skia.Paint
import org.jetbrains.skia.SamplingMode
import org.jetbrains.skia.VertexMode

actual class NativeVertexBatch actual constructor(atlas: Atlas) {
    private val bgPaint = Paint()
    private val glyphPaint = Paint().apply {
        shader = Image.makeFromBitmap(atlas.image.asSkiaBitmap())
            .makeShader(FilterTileMode.CLAMP, FilterTileMode.CLAMP, SamplingMode.DEFAULT)
    }

    actual fun drawBackgrounds(canvas: Canvas, positions: FloatArray, colors: IntArray) {
        canvas.nativeCanvas.drawVertices(
            VertexMode.TRIANGLES, positions, colors, null, null, BlendMode.DST, bgPaint,
        )
    }

    actual fun drawGlyphs(canvas: Canvas, positions: FloatArray, texCoords: FloatArray, colors: IntArray) {
        canvas.nativeCanvas.drawVertices(
            VertexMode.TRIANGLES, positions, colors, texCoords, null, BlendMode.MODULATE, glyphPaint,
        )
    }
}
