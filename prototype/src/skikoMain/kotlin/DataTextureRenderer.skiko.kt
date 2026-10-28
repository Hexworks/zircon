import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.nativeCanvas
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.FilterTileMode
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect
import org.jetbrains.skia.RuntimeEffect
import org.jetbrains.skia.SamplingMode
import org.jetbrains.skia.Shader
import org.jetbrains.skia.impl.Managed

actual class DataTextureRenderer actual constructor(
    private val cols: Int,
    private val rows: Int,
    atlas: Atlas,
) : AutoCloseable {
    private val tile = atlas.tile
    private val info = ImageInfo(cols, rows, ColorType.RGBA_8888, ColorAlphaType.OPAQUE)
    private val rowBytes = cols * 4
    private val atlasShader: Shader = Image.makeFromBitmap(atlas.image.asSkiaBitmap())
        .makeShader(FilterTileMode.CLAMP, FilterTileMode.CLAMP, SamplingMode.DEFAULT)
    private val paint = Paint()
    private val bounds = Rect.makeWH((cols * tile).toFloat(), (rows * tile).toFloat())

    // Grid geometry is baked into the program so no uniform packing is needed per frame.
    private val effect = RuntimeEffect.makeForShader(
        """
        uniform shader atlas;
        uniform shader codes;
        uniform shader fgs;
        uniform shader bgs;

        half4 main(float2 p) {
            const float2 tileSize = float2(${tile}.0, ${tile}.0);
            float2 cell = floor(p / tileSize);
            float2 cellCenter = cell + float2(0.5, 0.5);
            float code = floor(codes.eval(cellCenter).r * 255.0 + 0.5);
            float2 glyphOrigin = float2(mod(code, 16.0), floor(code / 16.0)) * tileSize;
            float2 inTile = p - cell * tileSize;
            half4 glyph = atlas.eval(glyphOrigin + inTile);
            half3 fg = fgs.eval(cellCenter).rgb;
            half3 bg = bgs.eval(cellCenter).rgb;
            return half4(mix(bg, fg, glyph.a), 1.0);
        }
        """.trimIndent()
    )

    private val live = ArrayList<Managed>(8)

    actual fun draw(canvas: Canvas, codes: ByteArray, fgs: ByteArray, bgs: ByteArray) {
        releaseFrame()
        val codesImg = Image.makeRaster(info, codes, rowBytes)
        val fgImg = Image.makeRaster(info, fgs, rowBytes)
        val bgImg = Image.makeRaster(info, bgs, rowBytes)
        live.add(codesImg); live.add(fgImg); live.add(bgImg)
        val shader = effect.makeShader(
            uniforms = null,
            children = arrayOf(atlasShader, codesImg.dataShader(), fgImg.dataShader(), bgImg.dataShader()),
            localMatrix = null,
        )
        live.add(shader)
        paint.shader = shader
        canvas.nativeCanvas.drawRect(bounds, paint)
    }

    private fun Image.dataShader(): Shader =
        makeShader(FilterTileMode.CLAMP, FilterTileMode.CLAMP, SamplingMode.DEFAULT).also { live.add(it) }

    private fun releaseFrame() {
        live.forEach { it.close() }
        live.clear()
    }

    actual override fun close() {
        releaseFrame()
        effect.close()
        atlasShader.close()
        paint.close()
    }
}
