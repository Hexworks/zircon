import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

/**
 * Path A1: idiomatic DrawScope calls. Two draws per tile, Compose allocates
 * paint state per call.
 */
fun DrawScope.drawGridWithDrawScope(grid: Grid, atlas: Atlas) {
    val t = atlas.tile
    val tileSize = Size(t.toFloat(), t.toFloat())
    val srcSize = IntSize(t, t)
    val dstSize = IntSize(t, t)
    var i = 0
    for (row in 0 until grid.rows) {
        val y = row * t
        for (col in 0 until grid.cols) {
            val x = col * t
            drawRect(Color(grid.bg[i]), Offset(x.toFloat(), y.toFloat()), tileSize)
            val code = grid.chars[i]
            drawImage(
                image = atlas.image,
                srcOffset = IntOffset((code % 16) * t, (code / 16) * t),
                srcSize = srcSize,
                dstOffset = IntOffset(x, y),
                dstSize = dstSize,
                colorFilter = ColorFilter.tint(Color(grid.fg[i])),
            )
            i++
        }
    }
}

/**
 * Path A2: same primitives through the underlying Canvas with one reused Paint,
 * which removes most per-call allocation.
 */
class CanvasPathState {
    val rectPaint = Paint()
    val imagePaint = Paint()
    private var lastFg = 0
    private var lastFilter: ColorFilter? = null

    fun filterFor(fg: Int): ColorFilter {
        if (fg != lastFg || lastFilter == null) {
            lastFg = fg
            lastFilter = ColorFilter.tint(Color(fg))
        }
        return lastFilter!!
    }
}

fun DrawScope.drawGridWithCanvas(grid: Grid, atlas: Atlas, state: CanvasPathState) {
    val t = atlas.tile
    val srcSize = IntSize(t, t)
    val canvas = drawContext.canvas
    val rectPaint = state.rectPaint
    val imagePaint = state.imagePaint
    var i = 0
    for (row in 0 until grid.rows) {
        val y = row * t
        for (col in 0 until grid.cols) {
            val x = col * t
            rectPaint.color = Color(grid.bg[i])
            canvas.drawRect(Rect(x.toFloat(), y.toFloat(), (x + t).toFloat(), (y + t).toFloat()), rectPaint)
            val code = grid.chars[i]
            imagePaint.colorFilter = state.filterFor(grid.fg[i])
            canvas.drawImageRect(
                image = atlas.image,
                srcOffset = IntOffset((code % 16) * t, (code / 16) * t),
                srcSize = srcSize,
                dstOffset = IntOffset(x, y),
                dstSize = srcSize,
                paint = imagePaint,
            )
            i++
        }
    }
}
