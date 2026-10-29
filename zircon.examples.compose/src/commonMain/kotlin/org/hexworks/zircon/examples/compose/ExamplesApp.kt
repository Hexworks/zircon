package org.hexworks.zircon.examples.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import org.hexworks.zircon.compose.ComposeResourceLoader
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.examples.ExamplesScreen
import org.hexworks.zircon.examples.demos
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.examples.gridSizeFor
import org.hexworks.zircon.examples.resources.Res

/**
 * The examples app, built entirely from Zircon components: a horizontal tab bar on top,
 * the selected demo below, in a grid that fills the available space. Switching tabs or
 * resizing the window rebuilds the grid, since each demo has its own tileset.
 */
@Composable
public fun ExamplesApp() {
    var selected by remember { mutableStateOf(demos.first()) }
    BoxWithConstraints(Modifier.fillMaxSize().background(Color.Black)) {
        val widthPx = constraints.maxWidth
        val heightPx = constraints.maxHeight
        val shown by produceState<ExamplesScreen?>(null, selected, widthPx, heightPx) {
            delay(RESIZE_DEBOUNCE_MS) // a window drag reports many sizes; build the last one
            installResources()
            value = examplesScreen(selected, gridSizeFor(selected.tileset, widthPx, heightPx)) { selected = it }
        }
        shown?.let { TileGridView(it.grid, beforeFrame = it.onFrame) }
    }
}

/**
 * Routes the demos' own Compose resources (REXPaint files, animations) through core's
 * resource loader, next to Zircon's bundled ones.
 */
public fun installResources() {
    ComposeResourceLoader.install()
    ComposeResourceLoader.addProjectResources { path -> Res.readBytes(path) }
}

private const val RESIZE_DEBOUNCE_MS = 150L
