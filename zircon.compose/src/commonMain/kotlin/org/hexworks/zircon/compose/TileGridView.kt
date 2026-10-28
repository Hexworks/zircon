package org.hexworks.zircon.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.compose.input.KeyboardEventListener
import org.hexworks.zircon.compose.input.MouseEventListener
import org.hexworks.zircon.compose.shader.TileShader
import org.hexworks.zircon.internal.grid.InternalTileGrid

/**
 * Draws [tileGrid] and feeds it keyboard and pointer input. Drop it anywhere in a Compose
 * tree; it measures itself to the grid's pixel size and renders every frame.
 *
 * @param extraTilesetFactories factories for custom tileset types, in addition to the
 * built-in CP437, TrueType and graphical ones.
 * @param beforeFrame called on every frame right before the grid is rendered, on the UI
 * thread; a convenient place for game logic that must stay in step with rendering.
 */
@Composable
public fun TileGridView(
    tileGrid: TileGrid,
    modifier: Modifier = Modifier,
    extraTilesetFactories: List<TilesetFactory<FrameData>> = emptyList(),
    beforeFrame: () -> Unit = {},
) {
    val internalGrid = remember(tileGrid) { tileGrid as InternalTileGrid }
    val renderer = remember(tileGrid) { ComposeRenderer(tileGrid, extraTilesetFactories) }
    val frame = remember(tileGrid) { FrameData(tileGrid.size.width, tileGrid.size.height) }
    val shader = remember(tileGrid) { TileShader(tileGrid.tileset.width, tileGrid.tileset.height) }
    val keyboard = remember(tileGrid) { KeyboardEventListener(internalGrid) }
    val mouse = remember(tileGrid) { MouseEventListener(internalGrid) }
    val focusRequester = remember { FocusRequester() }
    var tick by remember { mutableIntStateOf(0) }

    DisposableEffect(tileGrid) {
        ComposeResourceLoader.install()
        onDispose {
            renderer.close()
            shader.close()
        }
    }

    LaunchedEffect(tileGrid) {
        focusRequester.requestFocus()
        // The default tileset is needed before the first frame; others load as they appear.
        renderer.registry.loadTilesetFrom(tileGrid.tileset)
        renderer.registry.loadPending()
        while (true) {
            withFrameNanos {
                keyboard.drainEvents().forEach { (event, phase) -> renderer.enqueue(event, phase) }
                mouse.drainEvents().forEach { (event, phase) -> renderer.enqueue(event, phase) }
                beforeFrame()
                renderer.render(frame)
                tick++
            }
            if (renderer.registry.tilesets.any { !it.isLoaded }) {
                renderer.registry.loadPending()
            }
        }
    }

    val widthPx = tileGrid.widthInPixels
    val heightPx = tileGrid.heightInPixels

    Canvas(
        modifier
            .layout { measurable, _ ->
                val placeable = measurable.measure(Constraints.fixed(widthPx, heightPx))
                layout(widthPx, heightPx) { placeable.place(0, 0) }
            }
            .focusRequester(focusRequester)
            .focusable()
            .onKeyEvent { keyboard.handleKeyEvent(it) }
            .pointerInput(tileGrid) {
                awaitPointerEventScope {
                    while (true) {
                        mouse.handlePointerEvent(awaitPointerEvent(PointerEventPass.Main))
                    }
                }
            }
    ) {
        tick // read so every frame redraws
        drawIntoCanvas { canvas -> shader.draw(canvas, frame, renderer.registry.atlases(), internalGrid.config.debugConfig.displayGrid) }
    }
}
