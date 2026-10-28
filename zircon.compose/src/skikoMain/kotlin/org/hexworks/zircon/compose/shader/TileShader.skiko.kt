package org.hexworks.zircon.compose.shader

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.nativeCanvas
import org.hexworks.zircon.compose.FrameData
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.ColorType
import org.jetbrains.skia.FilterTileMode
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo
import org.jetbrains.skia.Paint
import org.jetbrains.skia.Rect
import org.jetbrains.skia.RuntimeEffect
import org.jetbrains.skia.RuntimeShaderBuilder
import org.jetbrains.skia.SamplingMode
import org.jetbrains.skia.Shader
import org.jetbrains.skia.impl.Managed

/**
 * Skia implementation: the frame's three data textures and the atlas go in as child
 * shaders, the geometry as uniforms. See `prototype/` for the benchmark that chose this.
 */
public actual class TileShader actual constructor(
    private val tileWidth: Int,
    private val tileHeight: Int,
) : AutoCloseable {

    private val effect = RuntimeEffect.makeForShader(SKSL)
    private val paint = Paint()
    private val perFrame = ArrayList<Managed>(16)

    /** Skia images for the atlases, rebuilt only when an atlas changes version. */
    private val atlasImages = mutableMapOf<Int, Pair<Int, Image>>()

    public actual fun draw(canvas: Canvas, frame: FrameData, atlases: Map<Int, AtlasInfo>, gridLines: Boolean) {
        release()
        val info = ImageInfo(frame.cols, frame.rows, ColorType.RGBA_8888, ColorAlphaType.UNPREMUL)
        val rowBytes = frame.cols * 4
        val cells = Image.makeRaster(info, frame.cells, rowBytes).also { perFrame += it }.dataShader()
        val fgs = Image.makeRaster(info, frame.foregrounds, rowBytes).also { perFrame += it }.dataShader()
        val bgs = Image.makeRaster(info, frame.backgrounds, rowBytes).also { perFrame += it }.dataShader()
        val bounds = Rect.makeWH((frame.cols * tileWidth).toFloat(), (frame.rows * tileHeight).toFloat())
        val native = canvas.nativeCanvas

        fun pass(passId: Float, atlas: Shader, cellWidth: Int, cellHeight: Int) {
            val builder = RuntimeShaderBuilder(effect)
            builder.child("cells", cells)
            builder.child("fgs", fgs)
            builder.child("bgs", bgs)
            builder.child("atlas", atlas)
            builder.uniform("tileSize", tileWidth.toFloat(), tileHeight.toFloat())
            builder.uniform("cellSize", cellWidth.toFloat(), cellHeight.toFloat())
            builder.uniform("pass", passId)
            builder.uniform("grid", if (gridLines) 1f else 0f)
            val shader = builder.makeShader().also { perFrame += it }
            paint.shader = shader
            native.drawRect(bounds, paint)
        }

        pass(-1f, cells, 1, 1)
        for (atlasId in frame.usedAtlases.sorted()) {
            val atlas = atlases[atlasId] ?: continue
            val image = atlasImage(atlasId, atlas)
            val shader = image.makeShader(FilterTileMode.CLAMP, FilterTileMode.CLAMP, SamplingMode.DEFAULT).also { perFrame += it }
            pass(atlasId.toFloat(), shader, atlas.cellWidth, atlas.cellHeight)
        }
    }

    private fun atlasImage(atlasId: Int, atlas: AtlasInfo): Image {
        val cached = atlasImages[atlasId]
        if (cached != null && cached.first == atlas.version) return cached.second
        cached?.second?.close()
        val image = Image.makeFromBitmap(atlas.image.asSkiaBitmap())
        atlasImages[atlasId] = atlas.version to image
        return image
    }

    private fun Image.dataShader(): Shader =
        makeShader(FilterTileMode.CLAMP, FilterTileMode.CLAMP, SamplingMode.DEFAULT).also { perFrame += it }

    private fun release() {
        perFrame.forEach { it.close() }
        perFrame.clear()
    }

    actual override fun close() {
        release()
        atlasImages.values.forEach { it.second.close() }
        atlasImages.clear()
        paint.close()
        effect.close()
    }

    private companion object {
        val SKSL: String = """
            uniform shader cells;
            uniform shader fgs;
            uniform shader bgs;
            uniform shader atlas;
            uniform float2 tileSize;
            uniform float2 cellSize;
            uniform float pass;
            uniform float grid;

            half4 main(float2 p) {
                float2 cell = floor(p / tileSize);
                float2 center = cell + float2(0.5, 0.5);
                if (pass < 0.0) {
                    float2 inCell = p - cell * tileSize;
                    if (grid > 0.5 && (inCell.x < 1.0 || inCell.y < 1.0)) return half4(0.5, 0.5, 0.5, 1.0);
                    half4 bg = bgs.eval(center);
                    return bg.a > 0.5 ? half4(bg.rgb, 1.0) : half4(0.0);
                }
                half4 info = cells.eval(center);
                float atlasId = floor(info.b * 255.0 + 0.5);
                if (abs(atlasId - pass) > 0.5) return half4(0.0);
                half4 fg = fgs.eval(center);
                if (fg.a < 0.25) return half4(0.0);
                float2 slot = floor(info.rg * 255.0 + float2(0.5, 0.5));
                float2 inTile = (p - cell * tileSize) / tileSize;
                float2 uv = (slot + inTile) * cellSize;
                half4 texel = atlas.eval(uv);
                // fg.a == 1: the atlas is a mask tinted with the foreground colour (glyphs)
                // fg.a == 0.5: the atlas holds the colours (sprites)
                if (fg.a > 0.75) return half4(fg.rgb * texel.a, texel.a);
                return texel;
            }
        """.trimIndent()
    }
}
