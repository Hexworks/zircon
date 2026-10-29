package org.hexworks.zircon.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.hexworks.zircon.api.grid.TileGrid

/**
 * The shortest way to a window on the desktop: opens a window sized to [tileGrid] with the
 * given [title], full screen when [fullScreen] is set, and renders the grid in it.
 * Blocks until the window is closed.
 *
 * ```kotlin
 * val grid = tileGrid { size = Size(80, 40) }
 * runDesktop(grid, title = "My game") // use the screen/components API on `grid` from here on
 * ```
 *
 * For anything beyond a single grid (icons, menus, several grids), compose [TileGridView]
 * into your own window instead.
 */
public fun runDesktop(
    tileGrid: TileGrid,
    title: String = "Zircon",
    fullScreen: Boolean = false,
    content: @Composable () -> Unit = { TileGridView(tileGrid) },
) {
    application {
        val state = rememberWindowState(
            placement = if (fullScreen) WindowPlacement.Fullscreen else WindowPlacement.Floating,
            size = DpSize(tileGrid.widthInPixels.dp, (tileGrid.heightInPixels + WINDOW_CHROME_DP).dp),
        )
        Window(onCloseRequest = ::exitApplication, title = title, state = state) {
            Box(Modifier.fillMaxSize().background(Color.Black)) {
                content()
            }
        }
    }
}

// Room for the title bar on the platforms where the window size includes it.
private const val WINDOW_CHROME_DP = 40
