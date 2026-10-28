import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

enum class Path(val label: String) {
    DRAW_SCOPE("A1 DrawScope"),
    CANVAS("A2 Canvas+Paint"),
    PIXELS("B Pixel buffer"),
    VERTICES("C drawVertices"),
    NATIVE_VERTICES("C2 nativeVertices"),
    SHADER("D dataTexShader"),
}

const val COLS = 240
const val ROWS = 135

/**
 * @param auto when true, cycle through every path and colour mode, print a result line
 * per combination and call [onDone] at the end.
 */
@Composable
fun App(auto: Boolean = false, only: Path? = null, onDone: () -> Unit = {}) {
    val atlas by produceState<Atlas?>(null) { value = Atlas.load() }
    val grid = remember { Grid(COLS, ROWS) }
    val stats = remember { FrameStats() }
    var path by remember { mutableStateOf(only ?: Path.CANVAS) }
    var colors by remember { mutableStateOf(ColorMode.RANDOM) }

    if (auto) {
        LaunchedEffect(atlas) {
            if (atlas == null) return@LaunchedEffect
            for (p in only?.let { listOf(it) } ?: Path.entries) {
                for (c in listOf(ColorMode.RANDOM, ColorMode.PALETTE16)) {
                    path = p
                    colors = c
                    delay(1000) // warm up
                    stats.reset()
                    delay(4000)
                    println(
                        "RESULT ${p.label.padEnd(16)} ${c.name.padEnd(10)} " +
                            "fps=${stats.fps.fmt()} draw=${stats.drawMs.fmt()}ms upload=${stats.uploadMs.fmt()}ms fill=${stats.fillMs.fmt()}ms"
                    )
                }
            }
            if (only == null) onDone()
        }
    }
    var frame by remember { mutableIntStateOf(0) }
    val canvasState = remember { CanvasPathState() }
    val pixelPath = remember { PixelPath(COLS * 8, ROWS * 8) }
    val verticesState = remember(atlas) { atlas?.let { VerticesPathState(grid, it) } }
    val nativeVertices = remember(atlas) { atlas?.let { NativeVerticesPath(grid, it) } }
    val shaderPath = remember(atlas) { atlas?.let { ShaderPath(grid, it) } }
    DisposableEffect(shaderPath) { onDispose { shaderPath?.close() } }
    var uploaded by remember { mutableStateOf<ImageBitmap?>(null) }

    DisposableEffect(Unit) { onDispose { pixelPath.close() } }

    LaunchedEffect(atlas, path, colors) {
        val a = atlas ?: return@LaunchedEffect
        while (true) {
            withFrameNanos { now ->
                stats.frame(now)
                stats.measureFill { grid.randomize(colors) }
                if (path == Path.PIXELS) {
                    stats.measureDraw { pixelPath.fill(grid, a) }
                    stats.measureUpload { uploaded = pixelPath.upload() }
                }
                frame++
            }
        }
    }

    Box(Modifier.fillMaxSize().background(Color.Black)) {
        val a = atlas
        if (a != null) {
            Canvas(Modifier.fillMaxSize()) {
                frame // read so every frame invalidates this draw
                when (path) {
                    Path.DRAW_SCOPE -> stats.measureDraw { drawGridWithDrawScope(grid, a) }
                    Path.CANVAS -> stats.measureDraw { drawGridWithCanvas(grid, a, canvasState) }
                    Path.PIXELS -> uploaded?.let { drawImage(it) }
                    Path.VERTICES -> verticesState?.let { s -> stats.measureDraw { with(s) { draw() } } }
                    Path.NATIVE_VERTICES -> nativeVertices?.let { s ->
                        stats.measureDraw { stats.recordUpload(s.draw(drawContext.canvas)) }
                    }
                    Path.SHADER -> shaderPath?.let { s ->
                        stats.measureDraw { stats.recordUpload(s.draw(drawContext.canvas)) }
                    }
                }
            }
        }
        Column(Modifier.padding(8.dp).background(Color(0xCC000000))) {
            Text(
                "${path.label} | ${colors.name} | ${COLS}x${ROWS} @8px | " +
                    "fps ${stats.fps.fmt()} | draw ${stats.drawMs.fmt()} ms | upload ${stats.uploadMs.fmt()} ms | fill ${stats.fillMs.fmt()} ms",
                color = Color.White,
            )
            Row {
                Path.entries.forEach { p ->
                    Button(onClick = { path = p }, modifier = Modifier.padding(2.dp)) { Text(p.label) }
                }
                ColorMode.entries.forEach { c ->
                    Button(onClick = { colors = c }, modifier = Modifier.padding(2.dp)) { Text(c.name) }
                }
            }
        }
    }
}

private fun Double.fmt(): String {
    val whole = toInt()
    val frac = ((this - whole) * 10).toInt()
    return "$whole.$frac"
}
