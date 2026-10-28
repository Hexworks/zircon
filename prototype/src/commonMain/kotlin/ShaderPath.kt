import androidx.compose.ui.graphics.Canvas

/**
 * Path D: upload the grid as three tiny RGBA textures (char code, foreground, background)
 * and let a runtime shader sample the atlas per pixel. One draw call, ~390 KB crossing
 * to the graphics backend per frame at 240x135.
 */
class ShaderPath(private val grid: Grid, atlas: Atlas) : AutoCloseable {
    private val codes = ByteArray(grid.size * 4)
    private val fgs = ByteArray(grid.size * 4)
    private val bgs = ByteArray(grid.size * 4)
    private val renderer = DataTextureRenderer(grid.cols, grid.rows, atlas)

    /** Returns nanoseconds spent in the platform calls (texture upload + draw). */
    fun draw(canvas: Canvas): Long {
        var p = 0
        for (i in 0 until grid.size) {
            val fg = grid.fg[i]
            val bg = grid.bg[i]
            codes[p] = grid.chars[i].toByte(); codes[p + 1] = 0; codes[p + 2] = 0; codes[p + 3] = -1
            fgs[p] = (fg shr 16).toByte(); fgs[p + 1] = (fg shr 8).toByte(); fgs[p + 2] = fg.toByte(); fgs[p + 3] = -1
            bgs[p] = (bg shr 16).toByte(); bgs[p + 1] = (bg shr 8).toByte(); bgs[p + 2] = bg.toByte(); bgs[p + 3] = -1
            p += 4
        }
        val mark = kotlin.time.TimeSource.Monotonic.markNow()
        renderer.draw(canvas, codes, fgs, bgs)
        return mark.elapsedNow().inWholeNanoseconds
    }

    override fun close() = renderer.close()
}

/** Platform half of [ShaderPath]: owns the shader program and the data textures. */
expect class DataTextureRenderer(cols: Int, rows: Int, atlas: Atlas) : AutoCloseable {
    fun draw(canvas: Canvas, codes: ByteArray, fgs: ByteArray, bgs: ByteArray)
    override fun close()
}
