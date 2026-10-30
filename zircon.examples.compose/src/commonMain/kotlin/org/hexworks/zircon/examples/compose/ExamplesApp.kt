package org.hexworks.zircon.examples.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import org.hexworks.zircon.examples.ExamplesSelection
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.examples.gridSizeFor
import org.hexworks.zircon.examples.resources.Res

/**
 * The examples app, built entirely from Zircon components, in a grid that fills the
 * available space. Resizing the window or picking another tileset rebuilds the grid (the
 * cell size changes); the selected package, example and theme carry over.
 */
@Composable
public fun ExamplesApp() {
    // The selection the next build starts from: updated from inside the screen, so a
    // rebuild after a resize keeps what the user picked.
    val latest = remember { mutableStateOf(ExamplesSelection.DEFAULT) }
    var generation by remember { mutableIntStateOf(0) }
    BoxWithConstraints(Modifier.fillMaxSize().background(Color.Black)) {
        val widthPx = constraints.maxWidth
        val heightPx = constraints.maxHeight
        val shown by produceState<ExamplesScreen?>(null, generation, widthPx, heightPx) {
            delay(RESIZE_DEBOUNCE_MS) // a window drag reports many sizes; build the last one
            installResources()
            val selection = latest.value
            val screen = examplesScreen(
                selection = selection,
                size = gridSizeFor(selection.tileset.resource, widthPx, heightPx),
                onTilesetChange = { changed ->
                    latest.value = changed
                    generation++
                },
            )
            screen.selectionProperty.onChange { (_, changed) -> latest.value = changed }
            value = screen
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
