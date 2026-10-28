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
import org.hexworks.zircon.internal.grid.InternalTileGrid

/**
 * The shortest way to a window on the desktop: opens a window sized to [tileGrid]
 * (title and full-screen flag come from the grid's AppConfig) and renders the grid in it.
 * Blocks until the window is closed.
 *
 * ```kotlin
 * val grid = createTileGrid(appConfig { size = Size(80, 40) })
 * runDesktop(grid) // use the screen/components API on `grid` from here on
 * ```
 *
 * For anything beyond a single grid, compose [TileGridView] into your own window instead.
 */
public fun runDesktop(
    tileGrid: TileGrid,
    content: @Composable () -> Unit = { TileGridView(tileGrid) },
) {
    val config = (tileGrid as InternalTileGrid).config
    application {
        val state = rememberWindowState(
            placement = if (config.fullScreen) WindowPlacement.Fullscreen else WindowPlacement.Floating,
            size = DpSize(tileGrid.widthInPixels.dp, (tileGrid.heightInPixels + WINDOW_CHROME_DP).dp),
        )
        Window(onCloseRequest = ::exitApplication, title = config.title, state = state) {
            Box(Modifier.fillMaxSize().background(Color.Black)) {
                content()
            }
        }
    }
}

// Room for the title bar on the platforms where the window size includes it.
private const val WINDOW_CHROME_DP = 40
