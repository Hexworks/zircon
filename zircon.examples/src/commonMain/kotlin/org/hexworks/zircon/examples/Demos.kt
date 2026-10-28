package org.hexworks.zircon.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.compose.ComposeResourceLoader
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.examples.resources.Res

/**
 * One demo of the examples app: a name and a factory for the grid it shows. Factories
 * suspend because some demos load archives (REXPaint files, animations).
 */
public class Demo(public val name: String, public val create: suspend () -> TileGrid)

/**
 * All demos, in menu order.
 */
public val demos: List<Demo> = listOf(
    Demo("Components") { componentsDemo() },
    Demo("Game area") { gameAreaDemo() },
    Demo("REXPaint") { rexPaintDemo() },
    Demo("Animation") { animationDemo() },
    Demo("Benchmark") { benchmarkDemo() },
)

/**
 * The examples app: a demo picker on top, the selected demo's grid below.
 */
@Composable
public fun ExamplesApp() {
    var selected by remember { mutableStateOf(demos.first()) }
    val grid by produceState<TileGrid?>(null, selected) {
        // The demos' own Compose resources (REXPaint files, animations) go through core's
        // resource loader, so they have to be registered next to Zircon's bundled ones.
        ComposeResourceLoader.install()
        ComposeResourceLoader.addProjectResources { path -> Res.readBytes(path) }
        value = selected.create()
    }
    var fps by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        var frames = 0
        var windowStart = 0L
        while (true) {
            withFrameNanos { now ->
                if (windowStart == 0L) windowStart = now
                frames++
                if (now - windowStart >= 1_000_000_000L) {
                    fps = frames
                    frames = 0
                    windowStart = now
                }
            }
        }
    }

    Column(Modifier.fillMaxSize().background(Color(0xFF1E1E1E))) {
        Row(Modifier.padding(8.dp)) {
            demos.forEach { demo ->
                Button(onClick = { selected = demo }, modifier = Modifier.padding(end = 8.dp)) {
                    Text(demo.name)
                }
            }
            Text("$fps fps", color = Color.White, modifier = Modifier.padding(start = 16.dp, top = 12.dp))
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopStart) {
            grid?.let { current ->
                TileGridView(current, Modifier.padding(8.dp), beforeFrame = { BenchmarkTicker.tick(current) })
            }
        }
    }
}
