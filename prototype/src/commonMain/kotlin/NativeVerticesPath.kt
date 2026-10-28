import androidx.compose.ui.graphics.Canvas

/**
 * Path C2: same two-mesh idea as [VerticesPathState], but the vertex data lives in flat
 * primitive arrays and goes straight to the platform canvas (`drawVertices` exists on
 * both Skia and `android.graphics.Canvas`). No per-frame boxing.
 *
 * Six vertices per quad (two triangles, no index buffer) so the vertex count is not
 * limited by 16-bit indices.
 */
class NativeVerticesPath(private val grid: Grid, atlas: Atlas) {
    private val tile = atlas.tile
    private val vertexCount = grid.size * 6
    private val positions = FloatArray(vertexCount * 2)
    private val texCoords = FloatArray(vertexCount * 2)
    private val bgColors = IntArray(vertexCount)
    private val fgColors = IntArray(vertexCount)
    private val batch = NativeVertexBatch(atlas)

    init {
        val t = tile.toFloat()
        var p = 0
        for (row in 0 until grid.rows) {
            val y = row * t
            for (col in 0 until grid.cols) {
                val x = col * t
                // triangle 1: tl, tr, br; triangle 2: tl, br, bl
                positions[p++] = x; positions[p++] = y
                positions[p++] = x + t; positions[p++] = y
                positions[p++] = x + t; positions[p++] = y + t
                positions[p++] = x; positions[p++] = y
                positions[p++] = x + t; positions[p++] = y + t
                positions[p++] = x; positions[p++] = y + t
            }
        }
    }

    /** Returns nanoseconds spent in the native draw calls. */
    fun draw(canvas: Canvas): Long {
        val t = tile.toFloat()
        var p = 0
        var c = 0
        for (i in 0 until grid.size) {
            val code = grid.chars[i]
            val u = (code % 16) * t
            val v = (code / 16) * t
            texCoords[p++] = u; texCoords[p++] = v
            texCoords[p++] = u + t; texCoords[p++] = v
            texCoords[p++] = u + t; texCoords[p++] = v + t
            texCoords[p++] = u; texCoords[p++] = v
            texCoords[p++] = u + t; texCoords[p++] = v + t
            texCoords[p++] = u; texCoords[p++] = v + t
            val bg = grid.bg[i]
            val fg = grid.fg[i]
            for (k in 0 until 6) {
                bgColors[c] = bg
                fgColors[c] = fg
                c++
            }
        }
        val mark = kotlin.time.TimeSource.Monotonic.markNow()
        batch.drawBackgrounds(canvas, positions, bgColors)
        batch.drawGlyphs(canvas, positions, texCoords, fgColors)
        return mark.elapsedNow().inWholeNanoseconds
    }
}

/** Platform half of [NativeVerticesPath]. */
expect class NativeVertexBatch(atlas: Atlas) {
    fun drawBackgrounds(canvas: Canvas, positions: FloatArray, colors: IntArray)
    fun drawGlyphs(canvas: Canvas, positions: FloatArray, texCoords: FloatArray, colors: IntArray)
}
