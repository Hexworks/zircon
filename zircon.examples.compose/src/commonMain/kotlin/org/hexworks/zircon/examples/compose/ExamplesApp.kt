package org.hexworks.zircon.examples.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import org.hexworks.zircon.api.builder.grid.DEFAULT_TILESET
import org.hexworks.zircon.compose.ComposeResourceLoader
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.examples.ExamplesScreen
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.examples.gridSizeFor
import org.hexworks.zircon.examples.resources.Res

/**
 * The examples app, built entirely from Zircon components, in a grid that fills the
 * available space. The desktop and web launchers drop this one composable into a window.
 *
 * ## How this works, for readers new to Compose
 *
 * A `@Composable` function is not called once: Compose runs it, remembers what it read,
 * and runs it again ("recomposition") whenever one of those things changes. So this
 * function is a *description* of the UI in terms of some state, not a sequence of steps.
 * The pieces of state it uses:
 *
 * - `remember { ... }` keeps a value across recompositions (a plain `val` would be
 *   recreated on every run).
 * - A `MutableState` (here `generation`) is a value Compose watches: reading it inside a
 *   composable subscribes that composable to it, writing it schedules a recomposition.
 * - `produceState(initial, key1, key2, ...)` runs a coroutine that computes a value and
 *   exposes it as state. Whenever one of the keys changes the coroutine is cancelled and
 *   started again; the state object itself stays, so inside the coroutine `value` still
 *   holds the last result. That is how a rebuilt screen gets the previous one.
 * - `BoxWithConstraints` is a box that tells its content how much space it was given.
 *   The window size arrives through `constraints`, in pixels.
 *
 * ## The rebuild cycle
 *
 * The Zircon grid has a fixed number of cells and a fixed cell size, so two things force a
 * new grid: the window changing size, and the user picking another tile size. Both are
 * keys of the `produceState` below: the size directly, the tile size through
 * [ExamplesScreen.tileSizeProperty], whose change bumps `generation`. The producer waits
 * a moment (a window drag reports many sizes), then builds the screen and hands the
 * previous one over, so tileset, theme and the selected example survive the rebuild. A
 * tileset or theme change within the same tile size is applied inside the screen and
 * never reaches this function.
 *
 * [TileGridView] does the drawing: it measures itself to the grid's pixel size, renders
 * on every frame of Compose's frame clock and feeds pointer and key events to the grid.
 * Its `beforeFrame` runs right before each render; the screen's `onFrame` goes there so
 * the fps counter and the shown example's per-frame logic run in step with drawing.
 */
@Composable
fun ExamplesApp() {
    // Bumped when the tile size changes; a produceState key, so bumping it rebuilds the screen.
    var generation by remember { mutableIntStateOf(0) }

    BoxWithConstraints(Modifier.fillMaxSize().background(Color.Black)) {
        val widthPx = constraints.maxWidth
        val heightPx = constraints.maxHeight

        // null until the first build; the keys restart the producer, the state survives.
        val shown by produceState<ExamplesScreen?>(null, generation, widthPx, heightPx) {
            delay(RESIZE_DEBOUNCE_MS) // a window drag reports many sizes; build the last one
            installResources()
            val previous = value
            val tileSize = previous?.tileSizeProperty?.value ?: DEFAULT_TILESET.size
            val screen = examplesScreen(
                size = gridSizeFor(tileSize, widthPx, heightPx),
                previous = previous,
            )
            // The one selection a running grid cannot take; rebuild with it.
            screen.tileSizeProperty.onChange { generation++ }
            value = screen
        }
        shown?.let { screen -> TileGridView(screen.grid, beforeFrame = screen.onFrame) }
    }
}

/**
 * Routes the demos' own Compose resources (REXPaint files, animations) through core's
 * resource loader, next to Zircon's bundled ones.
 */
fun installResources() {
    ComposeResourceLoader.install()
    ComposeResourceLoader.addProjectResources { path -> Res.readBytes(path) }
}

private const val RESIZE_DEBOUNCE_MS = 150L
