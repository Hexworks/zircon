import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.VertexMode
import androidx.compose.ui.graphics.Vertices
import androidx.compose.ui.graphics.drawscope.DrawScope

/**
 * Path C: the whole grid as two triangle meshes. Backgrounds are untextured quads with
 * per-vertex colour; glyphs are atlas-textured quads whose texture is modulated by the
 * per-vertex foreground colour. Two draw calls per frame, all common code.
 */
class VerticesPathState(private val grid: Grid, atlas: Atlas) {
    private val quadCount = grid.size
    private val positions = ArrayList<Offset>(quadCount * 4)
    private val texCoords = ArrayList<Offset>(quadCount * 4)
    private val bgColors = ArrayList<Color>(quadCount * 4)
    private val fgColors = ArrayList<Color>(quadCount * 4)
    private val indices = ArrayList<Int>(quadCount * 6)

    private val bgPaint = Paint()
    private val glyphPaint = Paint().apply { shader = ImageShader(atlas.image) }
    private val tile = atlas.tile

    init {
        val t = tile.toFloat()
        var v = 0
        for (row in 0 until grid.rows) {
            val y = row * t
            for (col in 0 until grid.cols) {
                val x = col * t
                positions.add(Offset(x, y))
                positions.add(Offset(x + t, y))
                positions.add(Offset(x + t, y + t))
                positions.add(Offset(x, y + t))
                indices.add(v); indices.add(v + 1); indices.add(v + 2)
                indices.add(v); indices.add(v + 2); indices.add(v + 3)
                v += 4
            }
        }
    }

    fun DrawScope.draw() {
        val t = tile.toFloat()
        texCoords.clear(); bgColors.clear(); fgColors.clear()
        for (i in 0 until quadCount) {
            val code = grid.chars[i]
            val u = (code % 16) * t
            val w = (code / 16) * t
            texCoords.add(Offset(u, w))
            texCoords.add(Offset(u + t, w))
            texCoords.add(Offset(u + t, w + t))
            texCoords.add(Offset(u, w + t))
            val bg = Color(grid.bg[i])
            val fg = Color(grid.fg[i])
            repeat(4) { bgColors.add(bg); fgColors.add(fg) }
        }
        val canvas = drawContext.canvas
        canvas.drawVertices(
            Vertices(VertexMode.Triangles, positions, positions, bgColors, indices),
            BlendMode.Dst, // no shader: vertex colours only
            bgPaint,
        )
        canvas.drawVertices(
            Vertices(VertexMode.Triangles, positions, texCoords, fgColors, indices),
            BlendMode.Modulate, // texture * vertex colour
            glyphPaint,
        )
    }
}
